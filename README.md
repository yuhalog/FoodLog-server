# 🍽 푸드로그 Food Log | Backend
<img width="100" alt="logo" src="https://user-images.githubusercontent.com/62270401/197328264-e86b073e-868d-4b7d-8730-c0fdc69e2ef3.png">

## 👩🏻‍💻 멤버 및 역할

| 이름  | 역할                                         |
|-----|--------------------------------------------|
| 유하연 | Backend 개발, infra 구축 및 관리, Frontend API 연결 |
| 김예슬 | Backend 개발, Frontend API 연결                |
| 심규리 | UI 디자인, Frontend 개발                        |
| 양지원  | UI 디자인, Frontend 개발                        |


## 💁🏻‍♀️ 프로젝트 제안

### 제안 배경
음식은 추억을 회상할 수 있는 계기가 된다.  
여행에 가서 식당에 방문하고 먹었던 음식을 기록해 둔다면 훗날 그 기록을 다시 봤을 때 음식으로 여행을 추억할 수 있다. 이런 기록에 위치를 표시해준다면 기억을 더욱 생생하게 할 수 있다.사진에 위치와 감상평까지 더해진다면 더 장소에 대해 특별하고 오래 기억에 남는 기록이 될 것이다.  
이러한 생각에서 비롯되어 내가 직접 방문하고 주관적으로 느꼈던 식당을 지도에 표시하고 다른 사람과 공유하고 나만의 식당을 공유할 수 있다면 더 유용한 기록을 남길 수 있다 라는 생각에서 푸드 로그를 제작하고자 한다.

### 기대 효과
- 음식을 단지 사진으로만 보는 것이 아니라 지도에 표시하기 때문에 위치를 더 직관적으로 확인할 수 있다.
- 사용자가 현재 위치한 장소 주변의 식당을 찾는데 어려움을 덜어준다.
- 친구가 등록하는 홍보성 없는 평가를 확인할 수 있다.
- 사용자가 숨은 지역 맛집을 찾아냄으로써 지역 경제 활성화에 도움을 줄 수 있다.
- 높은 수준의 품질을 제공하는 사업자는 자연스러운 마케팅 효과를 누릴 수 있다.

### 주요 기능
- 사용자 기반 장소 추천 기능
- 모든 사용자의 게시물을 지도에서 확인
- 자신의 게시물을 등록해서 지도에서 확인
- 다른 사용자 구독, 구독한 사용자의 게시물만 확인
- 지도에서 검색, 필터링 기능

<br>

## 🧭 기능 명세
![기능명세](https://user-images.githubusercontent.com/62270401/197322714-e6ea1747-8b9e-4eee-b2e6-c480b9c446d3.png)

**주요 UI 프로토타입**
![주요페이지_프로토타입](https://user-images.githubusercontent.com/62270401/197322838-070c32b9-03d1-4bb3-88fa-6f378706ace3.png)


**1. 지도**
- 전체 게시물 조회: 게시물이 등록된 장소는 아이콘으로 지도에 표시가 된다. 줌 아웃, 줌 인 할 때 마다 보여지는 아이콘의 수가 달라진다.
- 특정 게시물 조회: 아이콘을 클릭하면 게시물을 조회한다.
- 검색: 지역별, 음식이름, 상호명으로 식당을 검색한다.
- 필터링: 평점이나 목적, 음식 종류를 활용해 게시물을 필터링해서 본다.
- 검색결과 조회: 사용자가 입력한 내용에 따라 검색결과가 나타난다.

**2. 회원 관리**
- 로그인: 구글 로그인 api를 이용해 소셜 로그인을 한다.

**3.내 계정**
- 프로필 등록 및 수정: 사진, 자기소개를 포함한 프로필을 등록하고 수정한다.
- 내 게시물 지도: 내가 등록한 게시물들이 아이콘으로 표시된 지도를 볼 수 있다.

**4. 구독**
- 사용자 구독: 다른 사용자를 구독한다.
- 구독자 게시물 조회: 구독한 사람들의 게시물을 모아서 본다.
- 사용자 검색: 다른 사용자의 아이디를 검색하여 다른 사람의 지도를 보거나 구독한다.

**5. 게시물**
- 게시물 등록: 사용자는 갤러리에서 사진을 선택, 별점을 선택하고 리뷰 작성, 목적 선택, 음식 종류를 선택한다. 사진에서 자동으로 추출된 메타데이터에 의해 위치와 날짜가 선택되며, 이 또한 사용자가 다시 선택할 수 있다.
- 게시물 수정: 사용자는 자신이 작성한 게시물에 대해 수정 가능, 게시물의 field 중 리뷰에 대한 것만 수정이 가능하다.
- 게시물 삭제: 사용자는 자신이 작성한 게시물에 대해 삭제 가능, 이 게시물은 DB에서 삭제된다.

**6. 댓글**
- 댓글 작성: 게시물에 댓글을 작성할 수 있다.
- 댓글 삭제: 내가 쓴 댓글에 한하여 댓글을 삭제한다.

**7. 추천**
- 목적 선택: 추천을 받고 싶은 목적을 선택한다.
- 추천 게시물 조회: 목적별로 사용자 기반으로 추천 게시물을 띄운다.

<br>

## 💽 ERD

**초기 Class Diagram**
![ClassDiagram](https://user-images.githubusercontent.com/62270401/197323043-4c925ee8-777a-4f00-bb14-b9d3423db117.png)
실제 구현시 쓰이는 클래스들을 추출하고 그에 따라 클래스에 필요한 속성 또는 변수들을 기술하고 연관 관계를 그렸다. 이 중 실제로 데이터베이스에서 가장 중심이 되는 클래스는 Member, Subscribe, Post, Comment 테이블이고 각각 필요한 변수들을 저장해두었다. 또한 각각 과정을 수행할 때 필요한 클래스를 생성하고 연관 관계를 명시했다.

Member는 여러개의 Post와 댓글을 생성할 수 있기 때문에 일대다 관계로 형성했고 하나의 Post에 여러개의 댓글이 있기 때문에 포스트와 댓글 또한 일대다 관계로 형성했다. Post 클래스에 필요한 목적과 음식종류, 그리고 Member클래스에 필요한 Gender는 enum타입으로 구성했다. 이외에도 포스트에 필요한 장소의 위치와 사진 url을 저장하는 엔티티는 따로 구성했다.

<br>

![ERD-1](https://user-images.githubusercontent.com/62270401/197323034-8a714b86-938a-44e6-aebe-adb74eca768b.png)
모든 테이블에는 객체를 구별하기 위한 id 데이터가 프라이머리키로 들어가고 운영시 편리함을 위해 생성시간과 수정시간이 포함된다.

Member 테이블에는 성별, 생일 등 추천에 필요한 데이터와 프로필 작성을 위한 데이터가 들어간다.
Member는 다른 멤버를 구독할 수 있기 때문에 Subscribe 테이블을 따로 만들어서 Member와 일대다 관계를 맺는다. Subscribe 테이블에는 멤버의 id와 멤버가 구독하는 사용자의 id 데이터가 들어가게 된다.

다음으로 Post 테이블에는 사용자가 게시물 작성을 하면 입력받는 데이터 리뷰, 별점, 음식종류등이 들어가고, 음식점의 위치(위도, 경도)와 게시물 리스트, 평균 별점을 나타내기 위한 Place 테이블과 다대일 관계를 맺는다.
그리고 Comment 테이블에는 댓글 내용과 댓글을 작성한 멤버id, 게시물 id가 포함된다.

<br>

### 발생했던 문제들과 변경된 ERD
- 장소를 위해 카카오 API를 사용했다. ➡️ 카카오 API의 정보를 따로 저장하는 테이블로 Place를 사용했고, 부수적으로 필요한 정보(별점, 게시물 개수)은 PlacePost 테이블로 따로 분리하여 사용했다.
- 추천을 위해서 Place에 대한 가장 많은 목적을 저장할 Cloumn이 필요했다.


위와 같은 상황들로 수정한 ERD는 다음과 같다.
![ERD-2](https://user-images.githubusercontent.com/62270401/197323013-a33a2ff7-b8a1-4d2b-9b1b-4ac0d22cf6cd.png)

**최종적으로 AWS RDS에 올라가있는 DB 테이블 구조**
![RDS](https://user-images.githubusercontent.com/62270401/197322966-15b9b65a-ba0e-45bc-940a-6b6d7f90a9f8.png)

<br>

## 🌐 사용 기술 및 분석
![기술스택](https://user-images.githubusercontent.com/62270401/197323754-3e6f9292-fa69-4275-b452-13de22c47940.png)

협업을 위해 노션, 구글 드라이브로 문서를 관리하고 일정을 확인하고, github를 통해 형상관리를 했다.

### 시스템 구성도
![사용기술](https://user-images.githubusercontent.com/62270401/197323488-7269e027-d25f-468d-a3fe-3308f2cee2c1.png)

### Spring boot, Apache Tomcat, Spring JPA
![](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white)
![](https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat-square&logo=SpringSecurity&logoColor=white)
![](https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white)
![](https://img.shields.io/badge/ApacheTomcat-F8DC75?style=flat-square&logo=ApacheTomcat&logoColor=white)  
Spring boot는 라이브러리를 직접 관리해주고 톰캣과 같은 WAS가 내장되어 있어서 프로젝트를 구동하기 편리하다는 장점이 있다. 기존 Spring의 복잡한 설정을 자동으로 지원해주며 IoC(제어의 역전), DI(의존성 주입) 스프링의 장점을 이용하여 개발이 가능하다.
자바 진영의 ORM 표준인 JPA를 활용하여 객제 지향적인 코드를 작성하고 높은 생산성을 발휘할 수 있다. 이러한 장점으로 우리가 제작할 앱은 DB에 접근하고 불러오는 효율성을 생각해야하기 때문에 더 높은 생산성을 갖는 앱을 만들기에 spring boot가 적합하다고 생각한다.

### MariaDB
![](https://img.shields.io/badge/MariaDB-003545?style=flat-square&logo=MariaDB&logoColor=white)  
Food Log에서는 게시물, 사용자, 댓글 등 테이블 간의 관계가 중요하고 명확하게 명시되어야 하는데, 이럴 때 Nosql의 유연하고 자유로운 데이터 구조 보다는 RDBMS의 명확하고 일관성 있는 스키마와 데이터 구조를 적용하는 것이 더 적합하다. 또한 데이터의 분류, 정렬, 탐색 속도가 Nosql에 비해 비교적 빠르기 때문에 이러한 쿼리를 많이 사용하는 Food Log에 RDBMS를 활용하기로 했다.
MariaDB는 추천이나 지도에서 게시물을 보여주어야 할 때 사용할 top n개의 데이터를 가져오는 경우 특화되어 있기 때문에 큰 장점이 있다. 또한 스토리지 엔진, 플러그인 및 기타 여러 도구로 구성된 에코 시스템으로 빠르게 확장 가능하며, 강력하기 때문에 다양한 용도로 사용될 수 있다. 단순한 데이터 트랜잭션에 뛰어나기 때문에 MariaDB를 선택하게 되었다.  
![PostgreSQL-MaridDB-비교](https://user-images.githubusercontent.com/62270401/197323726-c2687b9f-ace3-4065-ab6c-ffd6f552fee8.png)
### AWS
![](https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=AmazonAWS&logoColor=white)
![](https://img.shields.io/badge/RDS-527FFF?style=flat-square&logo=AmazonRDS&logoColor=white)
![](https://img.shields.io/badge/EC2-FF9900?style=flat-square&logo=AmazonEC2&logoColor=white)
![](https://img.shields.io/badge/S3-569A31?style=flat-square&logo=AmazonS3&logoColor=white)  
아마존 클라우드 서비스로 기본적으로 지원하는 기능(모니터링, 로그 관리, 백업, 복구, 클러스터링 등)이 다양하기 때문에 소규모 개발에 집중할 수 있다는 장점이 있다. 기능 개발을 하면서 우리가 집중해야 할 곳에 더 집중할 수 있다는 장점이 크기 때문에 AWS 서비스를 사용하게 되었다.

### React Native
![](https://img.shields.io/badge/ReactNative-61DAFB?style=flat-square&logo=React&logoColor=white)
![](https://img.shields.io/badge/Redux-764ABC?style=flat-square&logo=Redux&logoColor=white)  
프론트엔드 모바일 앱 개발용 오픈소스 프로젝트로써 ios와 안드로이드에서 동일하게 동작이 가능한 크로스 플랫폼이라는 가장 큰 장점을 가지고 있다. 네이티브 어플리케이션 개발을 위한 자바스크립트 프레임워크로 Android, iOS 기반의 API로 개발하여 속도가 빠르고 안정적이다. 리액트 네이티브는 개발자들이 안드로이드용 자바/코틀린, iOS용 오브젝티브-C/스위프트와 같은 언어로 작성한 네이티브 코드와 인터페이스가 가능하도록 브릿지(Bridge)를 제공하므로 더 많은 유연성을 제공한다. 변경된 코드를 저장하기만 해도 자동으로 변경된 내용이 적용된 화면을 확인할 수 있는 패스트 리프레쉬 기능을 제공하여 수정된 내용에 대한 결과를 즉각적으로 확인할 수 있다.

### RESTful API
![GraphQL-REST-비교](https://user-images.githubusercontent.com/62270401/197323724-9fbb311d-d7be-4eb2-9d78-d83f1b5f486a.png)

