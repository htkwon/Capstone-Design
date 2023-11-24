## 프로젝트명

Ping Pong Resource Server

## 프로젝트 소개

이 프로젝트는 한성대학교 캡스톤 디자인 작품에 OIDC 프로토콜을 적용하기 위해 구현된 리소스 서버 프로젝트입니다. Spring에서 제공하는 OAuth 2.0 Resource Server 라이브러리를 사용해서 구현하였으며 OIDC 프로토콜에서 Resource Server 역할을 담당하고 있습니다.

## 개발 기간

- 2023/01/19 ~

## 개발 환경

- 개발 언어 : `JAVA 11`
- IDE : `IntelliJ IDEA`
- 주요 프레임워크 & 라이브러리 : `Spring Boot` `OAuth 2.0 Resource Server`
- 데이터베이스 : `MySQL`

## 프로젝트 소개

이 프로젝트를 이해하려면, OIDC와 OAuth2 프로토콜에 대한 기본적인 지식이 필요합니다. OIDC에 대한 간단한 설명은 이 프로젝트의 인가 서버의 README.md 파일에 작성되어 있습니다.

### Resource Server란?

OpenID Connect(OIDC)에서 Resource Server는 OAuth 2.0에서의 Resource Server와 유사한 역할을 합니다. 즉, 리소스(자원)에 대한 보호를 담당하는 서버입니다.

Resource Server는 클라이언트 애플리케이션이 요청한 API 리소스를 보유하고 있으며, 이 리소스에 대한 접근 권한을 검증합니다. 클라이언트 애플리케이션은 Access Token을 사용하여 Resource Server에게 API 리소스에 대한 권한을 인증하고, 해당 리소스를 요청합니다. 이 때, Resource Server는 Access Token을 검증하여 해당 클라이언트 애플리케이션이 요청한 API 리소스에 대한 권한이 있는지 확인합니다. 만약 권한이 없으면, 클라이언트 애플리케이션에게 권한 없음 에러를 반환합니다.

따라서, Resource Server는 API 리소스에 대한 접근 권한 검증을 담당하고, 클라이언트 애플리케이션이 요청한 리소스를 제공하는 서버입니다. 이를 통해 OIDC는 클라이언트 애플리케이션의 보안성을 높이고, 인증 및 권한 부여 기능을 제공합니다.

### 이 프로젝트의 리소스

이 프로젝트가 보호하고 제공하는 주요 리소스는 “게시글”입니다.  여러 엔드포인트를 통해 이용자들에게 자유, Q&A, 구인 게시판 기능을 제공하며 각각의 엔드포인트들은 설정을 통해 보호되고 있습니다.

### 권한 관련 주요 설정

이 프로젝트에서 권한 관련 주요 설정은 다음과 같습니다.

- 엔드포인트에 접근 권한 설정
- 인가 서버로부터 받아온 공개 키를 사용해 접근 권한 검증
- 토큰 기반 인증 방식
- 부가 정보 입력 여부 판단을 위한 `jwtAuthenticationConverter` 커스텀

간단히 정리하면, 인가 서버로부터 받아온 공개 키를 사용해 요청의 접근 권한을 검증하여 리소스를 제공하도록 설정하였습니다. 또한 토큰 기반 인증 방식을 적용하였으며 부가 정보(닉네임, 자기 소개 등)의 입력 여부 판단을 위해 `jwtAuthenticationConverter`를 커스터마이징 하여 부가 정보 입력을 한 경우 권한을 부여하도록 하였습니다.

---
## FLOW
<img width="517" alt="Real Flow" src="https://github.com/htkwon/Capstone-Design/assets/117131575/dc841c14-003b-4c34-bb21-9d53f7a36c2b">

---
## RESULT
<img width="926" alt="메인페이지" src="https://github.com/htkwon/Capstone-Design/assets/117131575/036d060b-fd4f-4839-ae10-0c67742569be">

<img width="1274" alt="한성대 통합로그인" src="https://github.com/htkwon/Capstone-Design/assets/117131575/db832107-6974-41ec-b5f0-6a4b967b9736">

<img width="1261" alt="부가정보 페이지" src="https://github.com/htkwon/Capstone-Design/assets/117131575/0ac760cd-bf33-4631-b251-47f1050c411b">

<img width="626" alt="qna 게시글 모음 real" src="https://github.com/htkwon/Capstone-Design/assets/117131575/add082e3-fe4a-4a9c-8157-5b3dff777cde">

<img width="866" alt="모음집 real" src="https://github.com/htkwon/Capstone-Design/assets/117131575/51795399-5639-4433-97a6-44f54482fa22">










