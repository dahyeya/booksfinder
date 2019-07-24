# booksfinder
### [SpringBoot + Vue] Book's Finder Project
Kakao restAPI / Naver restAPI를 이용한 도서(책) 검색 웹 서비스 입니다.  
모바일웹 환경을 기준으로 개발되었습니다.

1. 기본 실행 방법  
```
java -jar booksfinder.jar
```
2. 소스 빌드  
```
- Client build
cd frontend/vue 
npm install
npm run build

- Server build
cd ../..
mvn package
```
3. Jar 실행파일 경로 (Github)
https://github.com/dahyeya/booksfinder/blob/master/booksfinder.jar

4. 개발 정보
- Server : SpringBoot
- 사용 라이브러리
  - HttpClient : java Http 통신 (API 서버 접속)
  - Jakson-databind : JSON create/parse (API 응답 메시지 파싱 및 메시지 응답 생성)
  - spring-boot-starter-data-jpa : h2 를 jpa 방식으로 사용 (repo)
  - h2 : 인메모리 데이터베이스

- Client : Javascript (Vue)
- 사용 라이브러리
  - vue : frontend 개발 프레임워크
  - vue-ads-pagination : 검색 결과를 pagination 형태로 표시 (검색결과 페이징)
  - axios : javascript Http 통신 (Backend controller 접속)
  - webpack : javascript 번들 및 크기 감소 (module bundler)
  - babel : javascript 브라우저 호환성 개선 (transpiler)

