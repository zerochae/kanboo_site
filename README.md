# Kanboo

### Member


|안대근|윤주원|임태희|임창균|권영채|
|---|---|---|---|---|
|![pl](https://user-images.githubusercontent.com/84373490/149456465-69f1ac57-7e3a-4d9a-8738-9fbe4b8dc90f.jpg)|![ta](https://user-images.githubusercontent.com/84373490/149456467-972b20a3-c601-4b7b-851d-d548eef3aba2.jpg)|![aa](https://user-images.githubusercontent.com/84373490/149456460-68f74b32-3ff5-46d5-a08d-43798e2b26a7.jpg)|![da](https://user-images.githubusercontent.com/84373490/149474651-460466a0-7aed-4387-b06d-18c13efd0a6c.jpg)|![ua](https://user-images.githubusercontent.com/84373490/149456468-1d9d7751-fe77-423b-bc9e-ea173eddba7f.jpg)|

### Skill Stack

<img src="https://img.shields.io/badge/Java-007396?style=for-the badge&logo=Java&logoColor=FFFFFF"/> <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the badge&logo=JavaScript&logoColor=000000"/> <img src="https://img.shields.io/badge/Vue-4FC08D?style=for-the badge&logo=vue.js&logoColor=FFFFFF"/> <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the badge&logo=Spring Boot&logoColor=FFFFFF"/> <img src="https://img.shields.io/badge/MariaDB-003545?style=for-the badge&logo=MariaDB&logoColor=FFFFFF"/> <img src="https://img.shields.io/badge/ORM-JPA-orange"/> 

## 개요

* 실시간 채팅 및 웹 컴파일러를 사용할 수 있는 PMS 프로그램

## 아키텍트

![Kanboo Architect](https://user-images.githubusercontent.com/84373490/149471998-270a75d0-c531-45d8-b195-9f54da94d495.png)

## 화면(Component) 별 설명

> 담당자 : 안대근

![Compiler](https://user-images.githubusercontent.com/84373490/149461335-c30c8701-8d03-4d40-98a5-3bf6eee35464.jpg)

* 컴파일러
    1. 웹에서 간단하게 자바 코드를 컴파일 할 수 있는 기능
    2. 자바의 Process클래스를 이용하여 리눅스 명령어와 자바 명령어를 통해 생성되어있는 폴더를 컴파일 후 결과를 반환 
    3. 파일시스템을 사용하여 사이트 내에서 프로젝트 생성 시 해당 프로젝트의 폴더를 storage하위에 생성해준다
        1. main.java파일을 생성한후 아웃풋 스트림으로 기본 틀을 잡아줌
        2. MENIFEST.mf파일을 생성해 class-path를 잡아주고, main메서드의 위치를 잡아준다.
    4. 컴파일러 탭에서 코드 작성 시 DB에 저장하며 실행버튼을 눌렀을 때 DB의 데이터를 파일에 작성해 준 후 컴파일을 진행한다.

![Erd](https://user-images.githubusercontent.com/84373490/149461318-9ccf717c-c1bd-4ab6-bd82-ee0ec9f06ccc.jpg)

* erd maker
    1. 웹에서 ERD를 작성하고 쿼리를 출력받아 볼 수 있는 기능
    2. 테이블을 생성하고 연관관계등을 설정할 수 있다.
    3. 테이블 생성 시 json형태로 데이터가 프론트에 저장되며 테이블이 저장된 json을 바탕으로 테이블의 제약조건을 또다른 json에 설정해 준다.
    4. 쿼리 출력 시 테이블json과 제약조건json을 바탕으로 create 쿼리를 먼저 출력 받고 alter를 통해 primary key, foreign key의 제약조건을 추가한 쿼리를 받아 볼 수 있다.

> 담당자 : 윤주원

![Scheduler](https://user-images.githubusercontent.com/84373490/149461329-100e1417-ba50-48b0-b2d7-bf19db461532.jpg)

* Scheduler
  1. 프로젝트 기간 내 수행할 일정들을 멤버들과 공유하기 위한 기능
  2. 달력 Double Click으로  모달 팝업을 통한 일정 추가 기능
  3. 일정 Drag & Drop 기능으로 가능한 일정 수정 기능
  4. 일정 Click & Hold 기능으로 삭제가 가능한 기능
  5. Filter Select 박스로 선택한 옵션에 따른 Data 렌더링 구현
  6. Filter가 개인일 경우 일정을 추가한 본인만 확인할 수 있는 기능


<img width="1280" alt="git" src="https://user-images.githubusercontent.com/84373490/149468722-cc603cdd-b221-4fff-b9b9-4d1048ce303c.png">

* GIt & Issue 
  1. 프로젝트가 사용하는 Repository를 Github Rest API를 이용한 JSON형식의 데이터를 가공 
  2. 가공한 JSON 데이터를 Node Tree 구현을 통해 화면에 렌더링
  3. 등록한 Repository에 사용자의 입력에 따라 폴더일 경우 Node Tree 확장
  4. 사용자의 입력이 File일 경우 우측 화면에 렌더링
  5. 선택한 File의 Source Code 크기, 줄 개수를 단위에 맞게 화면에 렌더링
  6. 선택한 File의 이름과 Issue 내용을 기재하고 등록하면 DB에 Insert 하는 기능 
  7. JWT SessionStorage에 저장된 인코딩된 정보를 가지고 사용한 유저에 따른 동적 데이터 바인딩
  
 * Socket ( Chat & Alarm )

![Chat](https://user-images.githubusercontent.com/84373490/149461334-4a27b392-3f45-491a-a891-40db56f4b4b2.jpg)

  1. Chat
     1. 사용자가 Project에 입장했을 때 실시간 채팅이 가능한 채팅 구현
     2. Stomp.Js와 Sock.Js로 View와 Server의 유연한 연결 구현
     3. 채팅 기록의 마지막 날짜와 현재 입력 날짜가 다를 경우 현재 입력 날짜를 채팅 사이에 출력
     4. 채팅 기록을 DB에 저장하고 불러오는 기능 구현
     5. JWT SessionStorage을 이용한 개별 채팅방 구현

![알림1](https://user-images.githubusercontent.com/84373490/149462258-41abbc50-d88e-4d8b-956b-d458a8c62a25.jpg)
![알림2](https://user-images.githubusercontent.com/84373490/149462261-3ba12058-797a-43c4-b3cc-d8fecfa6bf20.jpg)
![알림3](https://user-images.githubusercontent.com/84373490/149462262-5d48fabe-8b32-4633-887d-fe8256e7e7ef.jpg)
   
  2. Alarm ( 화면 우측 상단 )
     1. Proejct Member가 해당 Project에 입장시 Member 정보 알람
     2. Scheduler 일정 등록시 Schduler의 분류, 제목정보 알람
     3. Project 게시판 게시물 등록할 경우 알람
     4. GanttChart 
     5. Kanban Item의 API 완료, 구현 완료로 변경될 경우 알람
     
 > 담당자: 임태희

![FreeBoard](https://user-images.githubusercontent.com/84373490/149461321-33b7a78d-ee2b-473a-aa8d-57f2d282d6c9.jpg)

* 자유 게시판
   1. 회원들이 자유롭게 이용 할 수있는 자유게시판 구현.
   2. 기본적인 CRUD로 구성된 게시판
   3. 추가 기능은 게시글 좋아요와 신고 기능이 있고, 회원당 한 개의 게시글의 한개의 좋아요만 가능함.
   4. 신고 또한 게시글 한개당 1개의 신고만 가능하고 신고횟수 누적 5회가 될 시 자유게시판에서 노출되지 않음.
   5. editor를 만들어서 library 를 사용하지 않고 글 작성 기능 구현
   6. 이미지의 경우 base64로 encoding해서 DB에 직접 데이터 삽입
   7. 파일의 경우 1개의 게시글당 1개의 파일 첨부가 가능하고 파일 클릭시 다운로드 가능.
   8. application.yaml에 max-size 설정해서 파일 첨부 최대크기 제한
   9. 파일 첨부의 경우 static 안에 storage 폴더를 생성해서
      board
        ㄴ free
             ㄴ회원아이디
             ㄴ게시글아이디
        ㄴ qna
         "
        ㄴ project
         "
   10. 이런 계층 형태로 디렉토리를 동적으로 생성해서 파일이름의 중복을 방지. 대안으로는 uuid 사용 가능
   11. 스크롤 페이징으로 게시판 구현.
   12. div태그에 scroll 이벤트를 추가해서 offset이 스크롤바의 일정 비율이상 넘어 갈 시
     다음 게시글 10개를 가지고와서 배열에다가 push 해줌
   13. 댓글 기능 또한, 페이징 처리 기능이 있음
   14. 현재 보여지는 댓글의 개수보다 총 댓글의 개수가 적으면 더보기 버튼을 눌러주고 클릭하면 5개씩 가지고와서 페이징처리
* 문의 게시판
   - 자유게시판과 같은 형태이지만 문의게시판 특성상 내가 작성한 글만 보여야 하며 관리자가 확인 할수 있어야해서
      DB에서 가지고 오는 쿼리의 형태만 다름
* 프로젝트 게시판
   - 프로젝트 게시판 또한 자유게시판과 같은 형태이지만 프로젝트 마다 고유의 게시판이 있어야 하기 때문에
     디비에서 가지고오는 쿼리가 다름
     
 ![ProjectList](https://user-images.githubusercontent.com/84373490/149461326-42e9b70b-861f-465e-84d8-73caf6d60ceb.jpg)
    
* 프로젝트 리스트
   1. 로그인 성공시 보여지는 프로젝트 리스트 화면 퍼블리싱
   2. 현재 진행중인 프로젝트, 완료된 프로젝트의 목록을 보여주고 본인이 속해있는 프로젝트 리스트를 보여줌
   3. 또한 프로젝트들 중에서 가장 최근에 등록된 프로젝트 관련 이슈와 스케줄을 하단에 가로스크롤로 보여줌

   
> 담당자: 임창균


![Demand](https://user-images.githubusercontent.com/84373490/149461340-cb288dc0-1920-410f-9c0d-edfef0c0be35.jpg)

* 요구사항 정의서
   1. 엑셀 업로드/다운로드, PDF 다운로드 가능
   2. 같은 프로젝트에 속해있는 멤버들과 요구사항정의서 내용 공유 가능
   3. 요구사항정의서를 DB에 저장하기 위해서는 엔터를 누르거나 save버튼을 클릭해야함
   4. 요구사항정의서에서 하나의 행을 삭제하기 위해서는 체크박스를 누르고 삭제버튼 눌러야함


![MemberSet](https://user-images.githubusercontent.com/84373490/149461324-b4fbcd7c-c406-45db-bd7a-3c760ff75f33.jpg)

* 멤버 설정 페이지
   1. 프로젝트의 이름을 보여줌
   2. 프로젝트 기간을 보여주며 수정이 가능
   3. 멤버들을 검색햇 추가하거나 삭제가 가능함
   4. 멤버들의 프로젝트 역할을 확인하고 수정할수 있음

> 담당자: 권영채

![How To Use](https://user-images.githubusercontent.com/84373490/149461323-13d39de5-5957-4a26-a47b-12e6540f1066.jpg)

* How To use
  1. 각 기능에 대한 간략한 설명이 있다.

![Terminal](https://user-images.githubusercontent.com/84373490/149461332-0ab52a19-e3af-4f59-9be4-67fc83b1a715.jpg)

* Terminal(login Page)
   1. 로그인 페이지를 터미널 포맷(UI)로 구성함
   2. 타이핑(login,sign) 등 으로 하고자하는 기능을 수행할 수 있다.
   3. 전체 명령어
   
   |명령어|기능|
   |---|---|
   |login|로그인|
   |sign|회원가입|
   |find|아이디,비밀번호찾기|
   |clear|모든 행 초기화|
   |cd home|처음 단계로 이동|
   |cd ..|전 단계로 이동|
   |help|도움말 |
   |ko|언어 변경(한글)|
   |en|언어 변경(영어)|
   
   4. 비밀번호는 암호화 (sha512) 방식으로 저장

![Kanban](https://user-images.githubusercontent.com/84373490/149461368-ee6b5342-ca58-4ec5-bfbe-26144cc5a12f.jpg)
    
* Kanban Board
  1. 프로젝트 기간 내에 일감을 등록하여 보드형식으로 데이터의 시각화를 하였다. 
  2. 뱃지와 색상은 사용자가 정할 수 있다.
  3. 작업 마감일을 설정하여 D-Day를 표시해줄 수 있다.
  4. 각 카드들은 자유롭게 이동이 가능하며 이동했을 때 즉시 DB에 반영된다.
  5. 수정, 삭제가 가능하다.

![GanttChart](https://user-images.githubusercontent.com/84373490/149461322-deaee182-942f-43e3-88c3-d401e9f0f301.jpg)
  
* Gantt Chart
  1. 프로젝트 기간 내 일감을 등록하여 그래프형식으로 데이터의 시각화를 하였다.
  2. 일감의 중요도에 따라 색상을 정할 수 있다. (낮음,보통,높음,긴급,비상 5단계)
  3. 각 일감들은 수정 삭제가 가능하다.
  4. 시작일과 종료일을 정해준 다음 각 달의 날짜에 맞춰서 표시된다.
  5. 브라우저의 크기가 변경되면 사이즈에 맞춰서 재 렌더링 된다.


![Admin](https://user-images.githubusercontent.com/84373490/149461865-ee38bcf8-8a3d-49d5-b743-889502ca7d42.jpg)


* Admin Page
  1. 회원 관리, 프로젝트 관리, QnA 게시판에 대한 답변을 할 수 있다.
  2. Login Page에서 Admin 계정으로 Login하면 접속할 수 있다.
  3. 회원의 정지 여부에 대한 변경을 할 수 있다. (7일 정지, 30일 정지, 영구 정지)
  4. 프로젝트의 완료 상태에 대한 변경을 할 수 있다. (완료, 진행 중, 삭제)
  5. QnA 게시판에 대한 답변을 할 수 있다.
  










<!-- ![DashBoard](https://user-images.githubusercontent.com/84373490/149461337-4aa57833-1776-4dd4-b43c-11b9840fafa1.jpg) -->



