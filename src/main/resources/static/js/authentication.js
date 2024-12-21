document.addEventListener('DOMContentLoaded', async () => {
    const token = localStorage.getItem('token'); // 사용자 토큰
    const profileContainer = document.getElementById('profile-container'); // 프로필
    const loginBtnContainer = document.getElementById('login-btn'); // 로그인 버튼
    const bakeryList = document.getElementById('bakery-list'); // 빵집 목록
    const bakeryDetail = document.getElementById('bakery-detail'); // 빵집 상세 설명
    const mapContainer = document.getElementById('map'); // 지도 컨테이너

    // 카카오 지도 설정
    const mapOptions = {
        center: new kakao.maps.LatLng(36.35239345, 127.3909879),
        level: 5,
    };
    const map = new kakao.maps.Map(mapContainer, mapOptions);
    const markers = []; // 마커 배열

    let userInfo = null; // 사용자 정보
    let userBookMarks = []; // 사용자 북마크
    let userMemos = []; // 사용자 메모

    if (token) {
        try {
            const response = await fetch('/api/member', {
                method: 'GET',
                headers: {
                    Authorization: token,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Failed to fetch user info');
            }

            userInfo = await response.json();
            userBookMarks = userInfo.scraps.map(scrap => scrap.bakeryId) || [];
            userMemos = userInfo.memos.map(memo => ({
                bakeryId: memo.bakeryId,
                content: memo.content,
            })) || [];

            console.log('userBookMarks:', userBookMarks);
            console.log('userMemos:', userMemos);

            profileContainer.innerHTML = `
                <div class="user-info">
                    <img src="${userInfo.profileImage}" alt="Profile Image" class="profile-img">
                    <span class="user-name">${userInfo.name}</span>
                    <img src="/images/icon-logout.png" class="logout-img" id="logout-img">
                </div>
            `;
            profileContainer.style.display = 'block';
            loginBtnContainer.style.display = 'none';

            document.getElementById('logout-img').addEventListener('click', () => {
                localStorage.removeItem('token');
                window.location.reload();
            });
        } catch (error) {
            console.error('Error fetching user info:', error);
            alert('로그인 상태를 확인할 수 없습니다. 다시 시도해주세요.');
            localStorage.removeItem('token');
        }
    } else {
        loginBtnContainer.style.display = 'block';
        profileContainer.style.display = 'none';
    }

    try {
        const response = await fetch('/api/bread');
        if (!response.ok) {
            throw new Error('Failed to fetch bakery data');
        }

        const bakeries = await response.json();

        // 기본 bakery.id 1인 데이터 설정
        const defaultBakery = bakeries.find(bakery => bakery.id === 1);
        if (defaultBakery) {
            updateBakeryDetail(defaultBakery, userBookMarks, bakeryDetail, userMemos, token);
            const markerPosition = new kakao.maps.LatLng(defaultBakery.latitude, defaultBakery.longitude);
            map.setCenter(markerPosition); // 지도의 기본 위치 설정
        }

        bakeries.forEach(bakery => {
            const bakeryDiv = document.createElement('div');
            bakeryDiv.className = 'bakery-item';
            bakeryDiv.id = `bakery-${bakery.id}`;

            const bookmarkIcon = userBookMarks.includes(bakery.id)
                ? `<img src="/images/icon-bookmark-after.png" class="bookmark-after" id="bookmark-icon">`
                : `<img src="/images/icon-bookmark-before.png" class="bookmark-before" id="bookmark-icon">`;

            bakeryDiv.innerHTML = `
                <div class="title">
                    <strong>${bakery.storeName}</strong>
                    ${bookmarkIcon}
                </div>
                <div class="content">${bakery.address}</div>
            `;

            const markerPosition = new kakao.maps.LatLng(bakery.latitude, bakery.longitude);
            const imageSrc = userBookMarks.includes(bakery.id) ? '/images/icon-pin-after.png' : '/images/icon-pin-before.png';
            const markerImage = new kakao.maps.MarkerImage(imageSrc, new kakao.maps.Size(29, 42), { offset: new kakao.maps.Point(19, 40) });

            const marker = new kakao.maps.Marker({
                position: markerPosition,
                image: markerImage,
                map: map,
            });

            markers.push({ bakeryId: bakery.id, marker });

            kakao.maps.event.addListener(marker, 'click', () => {
                const targetDiv = document.getElementById(`bakery-${bakery.id}`);
                if (targetDiv) {
                    targetDiv.scrollIntoView({ behavior: 'smooth', block: 'center' });
                    targetDiv.style.backgroundColor = '#83541c2b';
                    setTimeout(() => (targetDiv.style.backgroundColor = ''), 2000);
                }
                updateBakeryDetail(bakery, userBookMarks, bakeryDetail, userMemos, token);
            });

            bakeryDiv.addEventListener('click', () => {
                map.setCenter(markerPosition);
                updateBakeryDetail(bakery, userBookMarks, bakeryDetail, userMemos, token);
            });

            bakeryDiv.querySelector('#bookmark-icon').addEventListener('click', async () => {
                if (token) {
                    await handleBookmark(bakery, token, userBookMarks, markers, bakeryDetail);
                } else {
                    showLoginPopup();
                }
            });

            bakeryList.appendChild(bakeryDiv);
        });
    } catch (error) {
        console.error('Error fetching bakery data:', error);
    }
});

// 북마크 처리
async function handleBookmark(bakery, token, userBookMarks, markers, bakeryDetail) {
    const isBookmarked = userBookMarks.includes(bakery.id); // 북마크 상태 확인
    try {
        const response = await fetch(`/api/scrap/${bakery.id}`, {
            method: isBookmarked ? 'DELETE' : 'GET',
            headers: {
                Authorization: token,
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            if (isBookmarked) {
                // 북마크 삭제
                userBookMarks.splice(userBookMarks.indexOf(bakery.id), 1);
            } else {
                // 북마크 추가
                userBookMarks.push(bakery.id);
            }

            // 북마크 UI 업데이트
            const bakeryDiv = document.getElementById(`bakery-${bakery.id}`);
            updateBookmarkUI(bakeryDiv, bakeryDetail, !isBookmarked);

            // 마커 이미지 업데이트
            updateMarkerImage(bakery.id, !isBookmarked, markers);
        } else {
            throw new Error('Bookmark operation failed');
        }
    } catch (error) {
        console.error('Error handling bookmark:', error);
    }
}

//마커 변경
function updateMarkerImage(bakeryId, isBookmarked, markers) {
    let targetMarker = markers.find(marker => marker.bakeryId === bakeryId);
    if (targetMarker) {
        const newImage = new kakao.maps.MarkerImage(
            isBookmarked ? '/images/icon-pin-after.png' : '/images/icon-pin-before.png',
            new kakao.maps.Size(29, 42),
            { offset: new kakao.maps.Point(19, 40) }
        );
        targetMarker.marker.setImage(newImage); // 마커 이미지 변경
    }
}

//목록 북마크 변경
function updateBookmarkUI(bakeryDiv, bakeryDetail, isBookmarked) {
    const icon = bakeryDiv.querySelector('#bookmark-icon');
    if (isBookmarked) {
        icon.src = '/images/icon-bookmark-after.png';
        icon.className = 'bookmark-after';
    } else {
        icon.src = '/images/icon-bookmark-before.png';
        icon.className = 'bookmark-before';
    }

    // 상세 페이지 북마크 아이콘도 동기화
    const detailIcon = bakeryDetail.querySelector('#bookmark-icon');
    if (detailIcon) {
        detailIcon.src = isBookmarked
            ? '/images/icon-bookmark-after.png'
            : '/images/icon-bookmark-before.png';
        detailIcon.className = isBookmarked ? 'bookmark-after' : 'bookmark-before';
    }
}

// 상세 페이지 업데이트
function updateBakeryDetail(bakery, userBookMarks, bakeryDetail, userMemos, token) {
    const isBookmarked = userBookMarks.includes(bakery.id);
    const memo = userMemos.find(memo => memo.bakeryId === bakery.id);
    const memoContent = memo ? memo.content.trim() : '';

    bakeryDetail.innerHTML = `
        <div class="title">
            <strong>${bakery.storeName}</strong>
            <img src="${isBookmarked ? '/images/icon-bookmark-after.png' : '/images/icon-bookmark-before.png'}" 
                class="${isBookmarked ? 'bookmark-after' : 'bookmark-before'}" id="bookmark-icon">
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
        <div class="bakery-memo">
            <textarea class="area" id="bakery-memo" placeholder="베이커리가 회원님의 한 줄 메모를 기다리고있어요">${memoContent}</textarea>
            <img src="/images/icon-pen.png" alt="메모작성" class="bakery-pen" id="save-memo">
        </div>
    `;

    // 메모 저장 이벤트
    document.getElementById('save-memo').addEventListener('click', async () => {
        const updatedContent = document.getElementById('bakery-memo').value;
        try {
            // if (!token) {
            //     alert('로그인이 필요합니다.');
            //     return;
            // }
            const response = await fetch('/api/memo', {
                method: 'POST',
                headers: {
                    Authorization: token,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ bakeryId: bakery.id, content: updatedContent }),
            });

            if (response.ok) {
                // 메모 배열 업데이트
                const existingMemoIndex = userMemos.findIndex(memo => memo.bakeryId === bakery.id);
                if (existingMemoIndex !== -1) {
                    userMemos[existingMemoIndex].content = updatedContent;
                } else {
                    userMemos.push({ bakeryId: bakery.id, content: updatedContent });
                }
                alert('메모가 저장되었습니다');
            } else {
                showLoginPopup();
            }
        } catch (error) {
            console.error('Error saving memo:', error);
        }
    });
}
// 로그인 팝업
function showLoginPopup() {
    const overlay = document.getElementById('overlay');
    overlay.style.display = 'flex';
}
