## 빵이유(Bread2U)
<img src="./Docs/image/bread2u_main.png">
<b>개발기간</b> 2024.12.17 ~ 2024.12.22
<br><b>개발자</b> 권예지(mylazysundaycat)
<br>대전광역시 제과점 정보를 OPEN API로 연동하고, 이를 카카오 맵 API를 활용하여 시각화했습니다.<br>
사용자는 방문한 제과점들을 북마크 하며 '대전 빵집 도장깨기' 경험을 할 수 있습니다.

### Trouble Shooting
[#46 회원의 베이커리 북마크 추가 시 무한 추가되는 오류](https://github.com/users/mylazysundaycat/projects/3?pane=issue&itemId=91496282&issue=mylazysundaycat%7Cbread2u%7C46)
<br>[#42 제과점 OPEN API 중복 데이터 문제](https://github.com/users/mylazysundaycat/projects/3/views/1?pane=issue&itemId=91447981&issue=mylazysundaycat%7Cbread2u%7C42)
<br>[#57 EC2 서버 구축 이후 API 요청 에러](https://github.com/users/mylazysundaycat/projects/3/views/1?pane=issue&itemId=91707833&issue=mylazysundaycat%7Cbread2u%7C57)
<br>[#58 Unexpected token '<', "<!DOCTYPE " 에러](https://github.com/users/mylazysundaycat/projects/3/views/1?pane=issue&itemId=91889551&issue=mylazysundaycat%7Cbread2u%7C58)
### Project Architecture

<img src="./Docs/image/bread2u_architecture.png">


### Stack

Backend<br>

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=openjdk&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/Spring Boot-47A248?style=for-the-badge&logo=Spring Boot&logoColor=white"> <img src="https://img.shields.io/badge/Spring Data-FF5E00?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white"> <img src="https://img.shields.io/badge/OAuth-4285F4?style=for-the-badge&logo=oauth&logoColor=white"> <img src="https://img.shields.io/badge/AWS EC2-232F3E?style=for-the-badge&logo=AWS&logoColor=white">

DB<br>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/H2-0078D7?style=for-the-badge&logo=h2&logoColor=white">

CI/CD<br>
<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/Nginx-47A248?style=for-the-badge&logo=nginx&logoColor=white">

