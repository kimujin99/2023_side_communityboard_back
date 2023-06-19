# 😺 My Board: 2023 Side Project 😺
<div align="center">
  <img width="40%" src="https://florentine-aries-53f.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F419c7507-e5e2-478c-bc10-905cccc09b8f%2Fmascot.png?table=block&id=e6815482-91d4-49c8-8841-eb21a3dc76f0&spaceId=7bdad04b-e7a5-493d-8b4c-986af0d3e74e&width=1060&userId=&cache=v2"/>
</div>

## 🗂️ 목차
- ### <b> <a href="#0"> 🔗 관련 링크 </a> </b>
- ### <b> <a href="#1"> 📌 프로젝트 요약 </a> </b>
- ### <b> <a href="#2"> 👥 팀 구성 </a> </b>
- ### <b> <a href="#3"> 🧩 주요 기능 </a> </b>
- ### <b> <a href="#4"> 🛠️ 사용 기술 및 라이브러리 </a> </b>
- ### <b> <a href="#5"> 📸 결과물 </a> </b>
</br>

----
<h2 id="0">
    <b>🔗 관련 링크</b>
</h2>

- **AWS EC2 배포 사이트**
  - http://15.165.6.209:8080/login
- **Velog 포스팅**
  - https://velog.io/@kimujin99/series/Spring-side-project-%EC%97%85%EA%B7%B8%EB%A0%88%EC%9D%B4%EB%93%9C-%EA%B2%8C%EC%8B%9C%ED%8C%90
</br>

----
<h2 id="1">
    <b>📌 프로젝트 요약</b>
</h2>

### 레트로 풍 회원전용 게시판 사이트
- 이전에 개발한 KH-SEMI 기본 CRUD 게시판에서 영감을 받아 제작
- 기본적인 폼 형태에서 벗어나 실제 포스팅을 하듯 **레이아웃을 적용**하고, **글 사이에 이미지를 삽입** 가능
- 게시물에 **댓글**을 달아 타 회원과 소통 가능
</br>

----
<h2 id="2">
    <b>👥 팀 구성</b>
</h2>

- **개인 프로젝트**
- 1인 개발
</br>

----
<h2 id="3">
    <b>🧩 주요 기능</b>
</h2>

- **회원 전용**
    - 사이트 회원만 게시판에 접근할 수 있음
    - Session을 이용한 자체 로그인을 사용
    - CSRF 공격에 대비, CSRF 토큰 사용
- **게시물 작성**
    - WYSIWYG 에디터를 사용해 글의 레이아웃을 커스텀할 수 있음
    - 글 사이에 이미지 삽입 가능 (JPG, JPEG, PNG, GIF, ~10MB)
- **댓글 달기**
    - 게시물에 댓글을 달아 타 회원과의 소통 가능
- **카테고리 분류**
    - 게시물의 카테고리를 분류해 원하는 테마의 글을 편하게 찾을 수 있음
- **회원 프로필 이미지 업로드**
    - 클라우드 스토리지를 이용해 회원의 프로필 이미지를 자유롭게 변경할 수 있음 (JPG, JPEG, PNG, GIF, ~10MB)
- **회원 활동 등급 분류**
    - 작성한 게시물과 댓글 수로 회원의 활동 등급을 분류
    - 마이페이지 상단에 노출됨
</br>

----
<h2 id="4">
    <b>🛠️ 사용 기술 및 라이브러리</b>
</h2>

- `Java 17`, `Spring Boot 3.0.5` , `Spring Security 6.1.0`
- `JPA`, `MySQL`, `AWS RDS`, `AWS S3`, `AWS EC2`
- `Javascript`, `Ajax`, `HTML`, `CSS`, `NES.css`, `TailWind.CSS`, `98.css`
- `IntelliJ`, `MySQL Workbench`, `Git`, `Gihub`
/br>

----
<h2 id="5">
    <b>📸 결과물</b>
</h2>

<img src="https://file.notion.so/f/s/44cc736f-b689-40e1-a1c4-700fda1c91ba/login_server_validation.gif?id=713316a2-b64a-42b5-b2fd-106740833eae&table=block&spaceId=7bdad04b-e7a5-493d-8b4c-986af0d3e74e&expirationTimestamp=1687226381010&signature=UKH2pSFH7rT1FeXtM2vyv_8xLgqARmFmiQUBjer9_hc">
<img src="https://file.notion.so/f/s/03e0e372-cb4f-47b9-a27b-05812daf316b/login_ok.gif?id=5aeeb860-b925-491d-9684-12ecf0a97c9c&table=block&spaceId=7bdad04b-e7a5-493d-8b4c-986af0d3e74e&expirationTimestamp=1687226381014&signature=BgddqNn97nNuv8-KQuILx4UyJgm0RSZt0wuOqJfq-8Y">
<img src="https://file.notion.so/f/s/b3755c1d-e491-47f1-b82e-2bbe83b20857/signup_email_duplicate_check.gif?id=fc43176d-de4f-40ca-9a74-5c23bdcf3305&table=block&spaceId=7bdad04b-e7a5-493d-8b4c-986af0d3e74e&expirationTimestamp=1687226381017&signature=0ufvbSL9vuZFH_ZQDumPl3JtCrCigcMmWdsGDtnkUOw">
<img src="https://florentine-aries-53f.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fee07e3d4-0ff4-4654-9862-7fa8a493c5f9%2FUntitled.png?table=block&id=979635a7-ec7b-4564-adbe-c2dafdf63403&spaceId=7bdad04b-e7a5-493d-8b4c-986af0d3e74e&width=2000&userId=&cache=v2">
<img src="https://florentine-aries-53f.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ff311dabb-5acc-49ba-aa05-44c5168413af%2FUntitled.png?table=block&id=8b0e0077-2ebf-4f5d-8d84-62149af86a1d&spaceId=7bdad04b-e7a5-493d-8b4c-986af0d3e74e&width=2000&userId=&cache=v2">
<img src="https://florentine-aries-53f.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F93a1dd7c-3855-46ef-9dde-f234179113a8%2FUntitled.png?table=block&id=6e70742e-3c21-48ea-af9e-30143bc5dc15&spaceId=7bdad04b-e7a5-493d-8b4c-986af0d3e74e&width=2000&userId=&cache=v2">
<img src="https://florentine-aries-53f.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fc1db464f-9591-403b-b413-fcf559920eab%2FUntitled.png?table=block&id=8271d346-a69c-4766-8dde-7435cc508d31&spaceId=7bdad04b-e7a5-493d-8b4c-986af0d3e74e&width=1910&userId=&cache=v2">
<img src="https://florentine-aries-53f.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F03cf40a1-e214-4115-850e-ff6eef632ee3%2FUntitled.png?table=block&id=a30f4f5c-5e7a-4833-a8a7-44e9b20b4a1f&spaceId=7bdad04b-e7a5-493d-8b4c-986af0d3e74e&width=1890&userId=&cache=v2">
<img src="[https://file.notion.so/f/s/44cc736f-b689-40e1-a1c4-700fda1c91ba/login_server_validation.gif?id=713316a2-b64a-42b5-b2fd-106740833eae&table=block&spaceId=7bdad04b-e7a5-493d-8b4c-986af0d3e74e&expirationTimestamp=1687226381010&signature=UKH2pSFH7rT1FeXtM2vyv_8xLgqARmFmiQUBjer9_hc](https://file.notion.so/f/s/c8696b13-86c1-4899-a65a-110a2bb4881c/myPage_user_profile_update.gif?id=1b749fbf-1c1e-4dfd-bd54-62addc67ac08&table=block&spaceId=7bdad04b-e7a5-493d-8b4c-986af0d3e74e&expirationTimestamp=1687226381020&signature=F-VyBss3bV5cL6XPuiZ-Vl1v0R25QzhEPrf9UBqNA8g)https://file.notion.so/f/s/c8696b13-86c1-4899-a65a-110a2bb4881c/myPage_user_profile_update.gif?id=1b749fbf-1c1e-4dfd-bd54-62addc67ac08&table=block&spaceId=7bdad04b-e7a5-493d-8b4c-986af0d3e74e&expirationTimestamp=1687226381020&signature=F-VyBss3bV5cL6XPuiZ-Vl1v0R25QzhEPrf9UBqNA8g">
