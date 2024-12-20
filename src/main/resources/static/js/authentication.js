document.addEventListener('DOMContentLoaded',  async () => {
    const token = localStorage.getItem('token'); //사용자 토큰
    const profileContainer = document.getElementById('profile-container'); // 프로필
    const loginBtnContainer = document.getElementById('login-btn'); // 로그인
    const bakeryList = document.getElementById('bakery-list'); // 빵집 목록
    const bakeryDetail = document.getElementById('bakery-detail'); // 빵집 상세 설명
    const mapContainer = document.getElementById('map'); // 지도

    const mapOptions = {
        center: new kakao.maps.LatLng(36.35239345, 127.3909879), // 초기 중심 좌표
        level: 5 // 초기 확대 레벨
    };
    const map = new kakao.maps.Map(mapContainer, mapOptions);

    // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
    var mapTypeControl = new kakao.maps.MapTypeControl();

    // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
    // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
    map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

    // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
    var zoomControl = new kakao.maps.ZoomControl();
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

    // 마커를 저장할 변수
    const markers = [];

    // 유저 정보
    let userInfo = null;
    let userBookMarks = [];

    //토큰이 있을때랑 없을때 지도가 다르게 떠야됨
    //사용자 프로필도 다르게 떠야됨
    if (token) {
        try {
            // 토큰이 있는 경우 사용자 정보 요청
            const response = await fetch('/api/member', {
                method: 'GET',
                headers: {
                    'Authorization': token,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Failed to fetch user info');
            }
            userInfo = await response.json();
            userBookMarks = (userInfo.scraps.map(scrap => scrap.bakeryId)) || [];

            // 사용자 프로필 표시
            profileContainer.innerHTML = `
                <div class="user-info">
                    <img src="${userInfo.profileImage}" alt="Profile Image" class="profile-img">
                    <span class="user-name">${userInfo.name}</span>
                    <img src="/images/icon-logout.png" class="logout-img" id="logout-img">
                </div>
            `;
            profileContainer.style.display = 'block';
            loginBtnContainer.style.display = 'none'; // 로그인 버튼 숨김

            //로그아웃
            document.getElementById('logout-img').addEventListener('click', async () => {
                try {
                    if (!response.ok) {
                        throw new Error('로그아웃 실패');
                    }
                    // 클라이언트 토큰 제거
                    localStorage.removeItem('token');
                    window.location.reload()
                } catch (error) {
                    console.error('로그아웃 중 오류 발생:', error);
                    alert('로그아웃 중 문제가 발생했습니다. 다시 시도해주세요.');
                }
            });

        } catch (error) {
            console.error('Error fetching user info:', error);
            alert('로그인 상태를 확인할 수 없습니다. 다시 시도해주세요.');
        }
    } else {
        // 토큰이 없는 경우 로그인 버튼 표시
        loginBtnContainer.style.display = 'block';
        profileContainer.style.display = 'none';
    }




    // 1. API 호출하여 데이터 가져오기
    fetch('/api/bread')
        .then(response => {
            if (!response.ok) {
                throw new Error(`${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            // 2. 데이터 순회하며 목록 생성
            data.forEach((bakery) => {
                const bakeryId = bakery.id
                const bakeryDiv = document.createElement('div');
                bakeryDiv.className = 'bakery-item';
                bakeryDiv.id = `bakery-${bakeryId}`;
                const bookmarkIcon = userBookMarks.includes(bakeryId)
                    ? `<img src="/images/icon-bookmark-after.png" class="bookmark-after" id="bookmark-icon">`
                    : `<img src="/images/icon-bookmark-before.png" class="bookmark-before" id="bookmark-icon">`;
                bakeryDiv.innerHTML = `
                    <div class="title">
                        <strong>${bakery.storeName}</strong>
                        ${bookmarkIcon}
                    </div>
                    <div class="content">${bakery.address}</div>
                `;


                // 3. 마커 추가
                const markerPosition = new kakao.maps.LatLng(bakery.latitude, bakery.longitude);
                const imageSrc = userBookMarks.includes(bakery.id)? '/images/icon-pin-after.png':'/images/icon-pin-before.png';
                const imageSize = new kakao.maps.Size(29, 42); // 마커이미지의 크기
                const imageOption = {offset: new kakao.maps.Point(19, 40)}; // 마커시작점
                const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

                let marker = new kakao.maps.Marker({
                    position: markerPosition,
                    image: markerImage,
                    map: map
                });
                // 마커 저장 (필요하면 배열로 관리)
                markers.push({ bakeryId: bakery.id, marker });


                // 4. 마커 클릭 이벤트 (목록 스크롤 이동)
                kakao.maps.event.addListener(marker, 'click', () => {
                    const targetDiv = document.getElementById(`bakery-${bakeryId}`);
                    if (targetDiv) {
                        targetDiv.scrollIntoView({ behavior: 'auto', block: 'center' }); // 스크롤 이동
                        targetDiv.style.backgroundColor = '#83541c2b'; // 강조 효과
                        setTimeout(() => targetDiv.style.backgroundColor = '', 2000); // 강조 해제
                    }
                    // 지도 중심 이동 및 레벨 변경
                    map.setCenter(markerPosition); // 마커 위치로 중심 이동
                    // 상세 페이지 아래 추가
                    updateBakeryDetail(bakery, userBookMarks, bakeryDetail);
                });


                // 5. 목록 클릭 이벤트 추가
                bakeryDiv.addEventListener('click', () => {
                    const lat = bakery.latitude; // 위도
                    const lng = bakery.longitude; // 경도
                    const position = new kakao.maps.LatLng(lat, lng);
                    // 지도 중심 이동
                    map.setCenter(position);
                    // 상세 페이지 아래 추가
                    updateBakeryDetail(bakery, userBookMarks, bakeryDetail);
                });


                // 6. 북마크 클릭 이벤트 추가
                bakeryDiv.querySelector('#bookmark-icon').addEventListener('click', async () => {
                    if (token) {
                        const isBookmarked = userBookMarks.includes(bakery.id); // 항상 최신 상태 확인
                        await handleBookmark(bakery, isBookmarked, token, userBookMarks, bakeryDiv, bakeryDetail, markers, map); // 북마크 처리
                    } else {
                        showLoginPopup(); // 비회원 상태, 로그인 팝업 표시
                    }
                });

                bakeryList.appendChild(bakeryDiv);
            });
        })
        .catch(error => {
            console.error('Error fetching bakery data:', error);
        });
});

// 북마크 추가/삭제 함수
async function handleBookmark(bakery, isBookmarked, token, userBookMarks, bakeryDiv, bakeryDetail, markers, map) {
    try {
        let response;
        if (isBookmarked) {
            // 북마크 삭제 요청
            response = await fetch(`/api/scrap/${bakery.id}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': token,
                    'Content-Type': 'application/json',
                },
            });
            if (response.ok) {
                // 북마크 삭제 성공
                const index = userBookMarks.indexOf(bakery.id);
                if (index > -1) {
                    userBookMarks.splice(index, 1); // 배열에서 제거
                }
                updateBookmarkUI(bakeryDiv, bakeryDetail, false); // UI 업데이트
                updateMarkerImage(bakery.id, false, markers, map); // 마커 이미지 업데이트
            } else {
                throw new Error('북마크 삭제에 실패했습니다.');
            }
        } else {
            // 북마크 추가 요청
            response = await fetch(`/api/scrap/${bakery.id}`, {
                method: 'GET',
                headers: {
                    'Authorization': token,
                    'Content-Type': 'application/json',
                },
            });
            if (response.ok) {
                // 북마크 추가 성공
                if (!userBookMarks.includes(bakery.id)) {
                    userBookMarks.push(bakery.id); // 배열에 추가
                }
                updateBookmarkUI(bakeryDiv, bakeryDetail, true); // UI 업데이트
                updateMarkerImage(bakery.id, true, markers, map); // 마커 이미지 업데이트
            } else {
                throw new Error('북마크 추가에 실패했습니다.');
            }
        }
    } catch (error) {
        console.error('북마크 처리 중 오류:', error);
        alert('북마크 처리 중 문제가 발생했습니다. 다시 시도해주세요.');
    }
}


//TODO
//마커 업데이트 함수
function updateMarkerImage(bakeryId, isBookmarked, markers) {
    // 저장된 마커 중 bakeryId와 일치하는 마커를 찾음
    let targetMarker = markers.find(marker => marker.bakeryId === bakeryId);

    if (targetMarker) {
        // 새로운 마커 이미지 설정
        const newImage = new kakao.maps.MarkerImage(
            isBookmarked ? '/images/icon-pin-after.png' : '/images/icon-pin-before.png',
            new kakao.maps.Size(29, 42),
            { offset: new kakao.maps.Point(19, 40) }
        );

        // 기존 마커 이미지 업데이트
        targetMarker.marker.setImage(newImage);
    }
}



// 북마크 UI 업데이트 함수
function updateBookmarkUI(bakeryDiv, bakeryDetail, isBookmarked) {
    const icon = bakeryDiv.querySelector('#bookmark-icon');
    if (isBookmarked) {
        icon.src = '/images/icon-bookmark-after.png';
        icon.className = 'bookmark-after';
    } else {
        icon.src = '/images/icon-bookmark-before.png';
        icon.className = 'bookmark-before';
    }
    // 상세 페이지도 업데이트
    const detailIcon = bakeryDetail.querySelector('#bookmark-icon');
    if (detailIcon) {
        detailIcon.src = isBookmarked
            ? '/images/icon-bookmark-after.png'
            : '/images/icon-bookmark-before.png';
        detailIcon.className = isBookmarked ? 'bookmark-after' : 'bookmark-before';
    }
}

// 상세 페이지 업데이트 함수
function updateBakeryDetail(bakery, userBookMarks, bakeryDetail) {
    const isBookmarked = userBookMarks.includes(bakery.id); // 북마크 여부 확인
    const bookmarkIcon = isBookmarked
        ? `<img src="/images/icon-bookmark-after.png" class="bookmark-after" id="bookmark-icon">`
        : `<img src="/images/icon-bookmark-before.png" class="bookmark-before" id="bookmark-icon">`;

    bakeryDetail.innerHTML = `
        <div class="title">
            <strong>${bakery.storeName}</strong>
            ${bookmarkIcon}
        </div>
        <div class="address">
            <img src="/images/icon-location.png" id="bakery-location">
            ${bakery.address}
        </div>
        <div class="time">
            <img src="/images/icon-time.png" id="bakery-time">
            수정제안
        </div>
        <div class="phone">
            <img src="/images/icon-telphone.png" id="bakery-phone">
            ${bakery.phone ? bakery.phone : "수정제안"}
        </div>
    `;
}

// 팝업 표시 함수 (비회원 상태)
function showLoginPopup() {
    const overlay = document.getElementById('overlay'); // 어두운 배경 (팝업)
    overlay.style.display = 'flex'; // 팝업 보이기
}