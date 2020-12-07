*****Restaurant V2*****

1. 실행은 booksys.presentation.controller 에 있는 MainApp에서 실행
2. Scene builder를 활용한 JavaFX로 구현되어 FXML파일이 존재한다.
3. Database: Oracle Database 18c Express Edition Version 18.4.0.0.0
4. SQL_Developer: Oracle SQL Developer Version 19.4
5. lib폴더에 ojdbc8 사용 -> 오라클 버전이 다를 경우 다른 ojdbc 사용 요망
6. booksys.properties 파일 url 주소 수정 -> 실행하는 pc의 pdb서버 이름
7. connection을 위해 booksys.storage 에 있는 Connectivity 설정 -> DriverManager.getConnection(dbURL,"사용자이름","비밀번호");
8. lib폴더에 DB_DDL.sql 파일로 테이블 생성 후 어플리케이션 실행
9. 초기 (id, pass) 는 ('test', 'pass')
>EOF<