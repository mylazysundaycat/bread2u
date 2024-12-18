document.addEventListener('DOMContentLoaded', () => {
    const loginBtn = document.getElementById('login-btn'); // 로그인 버튼
    const overlay = document.getElementById('overlay'); // 어두운 배경 (팝업)
    const popup = document.querySelector('.popup'); // 팝업창

    // 로그인 버튼 클릭 시 팝업 보이기
    loginBtn.addEventListener('click', (event) => {
        event.preventDefault(); // 링크 기본 동작 방지
        overlay.style.display = 'flex'; // 팝업 보이기
    });

    // 팝업 바깥(overlay) 클릭 시 팝업 닫기
    overlay.addEventListener('click', (event) => {
        if (event.target === overlay) { // 클릭한 요소가 overlay인지 확인
            overlay.style.display = 'none'; // 팝업 닫기
        }
    });
});