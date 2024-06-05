<div align="center">
  <h1>AI 자소서 첨삭 서비스 Reditor</h1>
  
  ![reditor_banner](https://github.com/MinjoonHK/resumeEditorFrontend/assets/108560916/540a225f-2c76-4c7a-a228-da4dab9d7315)

  <h3>"당신의 경험이 빛날수 있도록"</h3>
  <p>취업의 첫번째 관문 자기소개서!</p>
  <p>많은 취업준비생분들이 자기소개서 작성을 어려워 합니다.</p>
  <p>그 이유는 경험이 부족해서가 아닌 어떻게 해당 경험을 설명해야 할지 모르기 떄문이라고 생각합니다.</p>
  <p>이제는 Reditor가 20000여개의 자기소개서 데이터를 바탕으로 여러분의 경험을 빛내 드리겠습니다.</p>
  <p><a href="https://reditor.me">Reditor 사용해보기</a></p>
</div>
</br>
</br>

# 목차

1. [프로젝트 소개](#프로젝트-소개)
2. [개발 기간](#개발-기간)
3. [팀 멤버](#팀-멤버)
4. [주요기능 소개](#주요기능-소개)
   - [자기소개서 가이드](#자기소개서-가이드)
   - [자기소개서 첨삭](#자기소개서-첨삭)
   - [글 리스트 조회](#글-리스트-조회)
   - [상세 페이지 기능](#상세-페이지-기능)
   - [마이페이지](#마이페이지)
   - [어드민 유저관리](#어드민-유저관리)
   - [어드민 유저통계](#어드민-유저통계)
   - [랜딩페이지](#랜딩페이지)
5. [프로젝트 아키텍처](#프로젝트-아키텍처)
6. [개발 환경](#개발-환경)
7. [레포지토리](#레포지토리)
8. [트러블 슈팅 & 기술적 경험](#트러블-슈팅--기술적-경험)
   - [김상휘 AI](#김상휘)
   - [박민준 FE](#박민준)
   - [신아진 BE](#신아진)
   - [박연경 BE](#박연경)
   - [안은비 BE](#안은비)


## 💻프로젝트 소개

<p>멀티캠퍼스 백엔드 개발자 부트캠프(스프링) 21회차 <b>최우수</b> 프로젝트입니다.</p>
<p>5명 모두 취업(이직)준비생으로 이루어진 팀이었기에 모두 자기소개서에 대한 관심이 많았습니다.</p>
<p>좋은 자기소개서를 작성하기 위해 다양한 첨삭 서비스를 이용해 보았지만 결과는 만족스럽지 못하였습니다.</p>
<p>이러한 부분을 저희가 배운 기술을 활용하여 직접 개선해 보고자 해당 프로젝트를 시작하게 되었습니다.</p>

## ⏲개발 기간

2024.04 - 2024.06

## 👪팀 멤버

|    김상휘    |    박민준    |    신아진    |    박연경    |    안은비    |
|:------------:|:------------:|:------------:|:------------:|:------------:|
|<a href="https://github.com/creatub"><img src="https://avatars.githubusercontent.com/u/157783929?s=70&v=4" width="70"/></a>              |<a href="https://github.com/minjoonHK"><img src="https://avatars.githubusercontent.com/u/108560916?v=4" width="70"/></a>               | <a href="https://github.com/aaajinnn"><img src="https://avatars.githubusercontent.com/u/120112210?s=70&v=4" width="70"/></a>             |<a href="https://github.com/yg0826"> <img src="https://avatars.githubusercontent.com/u/145968727?s=70&v=4" width="70"/></a> | <a href="https://github.com/ibnuena"><img src="https://avatars.githubusercontent.com/u/71430096?s=70&v=4" width="70"/></a> |
| 팀장 / AI    | 팀원 / Frontend | 팀원 / Backend | 팀원 / Backend | 팀원 / Backend |


## 📷주요기능 소개

### ❗ gif 로딩이 느릴수 있으니 조금만 기다려주세요 ❗

### [자기소개서 가이드](#자기소개서-가이드)
<p>자기소개서를 작성하는 첫번째 스텝은 경험을 나열하는것입니다</p>
<p>자기소개서 가이드 서비스는 여러 경험을 어떠한 항목에 넣는것이 효율적인지 추천해줍니다.</p>
<p>또한 미리 크롤링한 회사들의 자기소개서 항목을 제공함으로써 유저 경험을 향상 시켰습니다.</p>

![reditor_guide](https://github.com/MinjoonHK/resumeEditorFrontend/assets/108560916/69b73b16-3297-4a04-bddf-404f12673367)
### [자기소개서 첨삭](#자기소개서-첨삭)
<p>저희 서비스의 메인 기능인 자기소개서 첨삭 서비스입니다.</p>
<p>첨삭 이후 사용자가 달라진 부분을 한눈에 볼수 있도록 Diff 버튼을 추가 하였습니다.</p>
<p>로딩 시간동안 사용자가 지루하지 않도록 spinner 아래에 Tip을 추가 하였습니다.</p>

![reditor_edit](https://github.com/MinjoonHK/resumeEditorFrontend/assets/108560916/b1f99c28-d043-4ed1-a68e-9c10d85ce157)
### [글 리스트 조회](#글-리스트-조회)
<p>다른 사람이 첨삭한 자기소개서 결과물을 열람할수 있는 페이지 입니다.</p>
<p>가장 많은 추천수, 가장 조회수가 많은 자소서 목록을 확인 할 수 있습니다.</p>
<p>검색을 통해 원하는 자기소개서 목록을 검색할수 있도록 구현 하였습니다.</p>

![reditor_list](https://github.com/MinjoonHK/resumeEditorFrontend/assets/108560916/11596664-21ca-4b9c-adf4-5672a3e959e5)
### [상세 페이지 기능](#상세-페이지-기능)
<p>즐겨찾기, 별점부여, 댓글작성이 가능한 상세페이지 입니다.</p>
<p>즐겨찾기에 추가한 자기소개서는 마이페이지에서 확인 가능합니다.</p>

![reditor_detail_page](https://github.com/MinjoonHK/resumeEditorFrontend/assets/108560916/b7fa1373-eef3-456c-b02e-092f4803a7b6)
### [마이페이지](#마이페이지)
<p>회원정보가 수정가능한 마이페이지 입니다.</p>
<p>수정한 기록을 확인 할수있고 즐겨찾기에 추가한 목록을 확인 할 수 있습니다.</p>

![reditor_my_page](https://github.com/MinjoonHK/resumeEditorFrontend/assets/108560916/add5e14f-b540-4209-a9c9-05d9543a547b)
### [어드민 유저관리](#어드민-유저관리)
<p>가입한 유저들의 기본적인 정보를 열람 할 수 있습니다.</p>
<p>사용자의 불순한 의도가 발견될 경우 즉각 블랙리스트 조치를 할 수 있습니다.</p>

![reditor_admin_userlist](https://github.com/MinjoonHK/resumeEditorFrontend/assets/108560916/a54416a3-f7be-49d4-bc9d-194b15c900a8)
### [어드민 유저통계](#어드민-유저통계)
<p>실시간으로 변하는 방문자 수를 traffic 분석을 통해 확인합니다.</p> 
</p>Spring Scheduler를 활용하여 저장된 데이터를 통해 고객의 유입 통계를 확인할 수 있습니다.</p>
<p>이렇게 저장한 정보를 시각화하여 고객 페르소나를 분석합니다.</p>

![reditor_admin_userstat](https://github.com/MinjoonHK/resumeEditorFrontend/assets/108560916/39be7a93-d9f6-47b9-96c0-becff1de0c4d)
### [랜딩페이지](#랜딩페이지)
<p>서비스의 첫 인상인 만큼 다양한 애니메이션 효과를 통해 유저의 시선을 주의를 끕니다</p>
<p>서비스의 통계, 후기등 사용자들이 참고할만한 정보들을 보여줍니다.</p>

![reditor_landing](https://github.com/MinjoonHK/resumeEditorFrontend/assets/108560916/2669efa6-b87a-40a3-bb7d-dab9d3c9d314)



## 🏛프로젝트 아키텍처
  ![reditor_architecture2](https://github.com/MinjoonHK/resumeEditorFrontend/assets/108560916/9e4adc18-e1b2-46b6-b1a2-1b0b7735ff50)

## ⚙개발 환경

![reditor_techstack](https://github.com/MinjoonHK/resumeEditorFrontend/assets/108560916/12504f9b-7c8c-4f52-ae25-68178cab348c)

<ul>
  <li>Front-end: React.js@18.2.0, TypeScript, Redux Toolkit@9.1.0, Antd</li>
  <li>Back-end: Spring Boot, Spring Security, Hibernate</li>
  <li>AI: ChatGPT API, Qdrant, Flask</li>
  <li>Env: Node.js@v18.17.0, JAVA 17</li>
  <li>Build: Vite, Gradle</li>
  <li>IDE: VScode, IntelliJ</li>
  <li>DB: MySQL</li>
  <li>CI/CD: Github Actions</li>
  <li>Deploy: Vercel, CloudType</li>
  <li>Collaboration: Github, Slack, Notion, Swagger@2.0.2, Figjam, Kakao Oven </li>
</ul>


## 🏠레포지토리

<a href="https://github.com/MinjoonHK/resumeEditorFrontend">프론트엔드 레포지토리</a><br/>
<a href="https://github.com/JavaBackEnd21st/resumeEditorBackend">백엔드 레포지토리</a><br/>
<a href="https://github.com/JavaBackEnd21st/resume_gpt_qdrant">AI 레포지토리</a><br/>

## 🛠트러블 슈팅 & 기술적 경험

### 김상휘


### 박민준
<ul>
  
  <li><a href="https://github.com/MinjoonHK/resumeEditorFrontend/wiki/FE-%E2%80%90-UX-%EA%B0%9C%EC%84%A0">FE-UX개선</a></li>
  <li><a href="https://github.com/MinjoonHK/resumeEditorFrontend/wiki/FE-%E2%80%90-%EC%84%B1%EB%8A%A5-%EC%B5%9C%EC%A0%81%ED%99%94%EC%97%90-%EB%8C%80%ED%95%B4">FE-성능 최적화</li>
</ul>

### 신아진


### 박연경


### 안은비

