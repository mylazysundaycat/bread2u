document.addEventListener('DOMContentLoaded', () => {

    const bakeryList = document.getElementById('bakery-list'); // 빵집 목록 영역
    const bakeryDetail = document.getElementById('bakery-detail');
    const mapContainer = document.getElementById('map'); // 지도 영역


    // 지도 기본 설정
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

    // 2. API 호출하여 데이터 가져오기
    fetch('/api/bread')
        .then(response => {
            if (!response.ok) {
                throw new Error(`${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            // 3. 데이터 순회하며 목록 생성
            data.forEach((bakery, index) => {
                const bakeryDiv = document.createElement('div');
                bakeryDiv.className = 'bakery-item';
                bakeryDiv.innerHTML = `<div class="title"><strong>${bakery.storeName}</strong><img src="/images/icon-bookmark-before.png" id="bookmark-before"></div><div class="content">${bakery.address}</div>`;
                bakeryDiv.id = `bakery-${index}`;


                // 2. 마커 추가
                const markerPosition = new kakao.maps.LatLng(bakery.latitude, bakery.longitude);
                const marker = new kakao.maps.Marker({
                    position: markerPosition,
                    map: map
                });

                // 마커 저장 (필요하면 배열로 관리)
                markers.push(marker);

                // 3. 마커 클릭 이벤트 (목록 스크롤 이동)
                kakao.maps.event.addListener(marker, 'click', () => {
                    const targetDiv = document.getElementById(`bakery-${index}`);
                    if (targetDiv) {
                        targetDiv.scrollIntoView({ behavior: 'auto', block: 'center' }); // 스크롤 이동
                        targetDiv.style.backgroundColor = '#f0f8ff'; // 강조 효과 (선택사항)
                        setTimeout(() => targetDiv.style.backgroundColor = '', 2000); // 강조 해제
                    }
                    // 지도 중심 이동 및 레벨 변경
                    map.setCenter(markerPosition); // 마커 위치로 중심 이동
                    // 상세 페이지 아래 추가
                    bakeryDetail.innerHTML = "";
                    bakeryDetail.innerHTML += `
                        <div class="title">
                            <strong>${bakery.storeName}</strong>
                            <img src="/images/icon-bookmark-before.png" id="bookmark-before">
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
                });


                // 4. 목록 클릭 이벤트 추가
                bakeryDiv.addEventListener('click', () => {
                    const lat = bakery.latitude; // 위도
                    const lng = bakery.longitude; // 경도
                    const position = new kakao.maps.LatLng(lat, lng);
                    // 지도 중심 이동
                    map.setCenter(position);
                    // 상세 페이지 아래 추가
                    bakeryDetail.innerHTML = "";
                    bakeryDetail.innerHTML += `
                        <div class="title">
                            <strong>${bakery.storeName}</strong>
                            <img src="/images/icon-bookmark-before.png" id="bookmark-before">
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
                });

                bakeryList.appendChild(bakeryDiv);

            });
        })
        .catch(error => {
            console.error('Error fetching bakery data:', error);
        });
});