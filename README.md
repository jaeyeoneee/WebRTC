# 자율주행 자동차 웹캠 실시간 스트리밍 using WebRTC :car:

<div align=center>
  <img src="https://github.com/jaeyeoneee/WebRTC/assets/109527136/027af870-6779-426b-8483-9948df6cb967" width="500">
</div>

> DGIST 미래자동차연구부 근로장학생
>
> 개발기간: 2023.11 ~
>
> [개발 블로그](https://velog.io/@jaeyeoneee/WebRTC%EB%A1%9C-%EC%9B%B9%EC%BA%A0-%EC%98%81%EC%83%81-%EC%8B%A4%EC%8B%9C%EA%B0%84-%EC%A0%84%EC%86%A1%ED%95%98%EA%B8%B0-WebRTC%EB%9E%80)


## 프로젝트 소개
 * WebRTC는 시그널링 서버를 통해 사용자 간 연결 정보(SDP, IceCandidate)를 교환하고, 교환된 정보를 바탕으로 P2P 연결을 맺어 영상을 전송하는 프로토콜입니다.

 * 사용자가 실행되고 있는 웹캠 중 하나를 선택하면 스프링 부트 시그널링 서버(websocket, STOMP broker 사용)를 통해서 정보를 주고받고 P2P 연결을 맺게 됩니다.

 * 서버가 동영상을 전송할 필요가 없으므로 서버측 오버헤드가 적고, 실시간 스트리밍이 가능합니다.
 
#### STEP 1
 * 스프링 부트를 시그널링 서버로한다.
 * 웹캠과 사용자는 자바스크립트 WebRTC API를 이용한다.
 * 사용자가 웹캠 중 하나를 선택하면, 서버를 통해 정보가 교환되고 P2P 연결을 맺어 영상이 실시간 전송되게 한다.
 * 구현이 끝나면 실제 환경에서 테스트해본다.

#### STEP 2
 * 웹캠 측 자바스크립트 코드를 파이썬 aiortc 코드로 바꾼다.
 * aiortc를 사용함으로 openCV로 웹캠 영상을 더욱 정교하게 처리할 수 있다.
 * 이후 자율주행 자동차의 컴퓨터가 켜질 때 자동으로 프로그램이 실행되어 서버에 연결될 수 있게 한다.

## 시작 가이드

## Stacks :book:
**Environment**
<p>
  <span><img src="https://img.shields.io/badge/intellijidea-000000?style=for-the-badge&logo=intellijidea&logoColor=white"></span>
  <span><img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white"></span>
  <span><img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white"></span>
</p>


**Development**
<p>
  <span><img src="https://img.shields.io/badge/openJDK-437291?style=for-the-badge&logo=openjdk&logoColor=white"></span>
  <span><img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white"></span>
  <span><img src="https://img.shields.io/badge/WebRTC-333333?style=for-the-badge&logo=WebRTC&logoColor=white"></span>
  <span><img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"></span>
</p>

**Communication**
<p>
  <span><img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"></span>
  <span><img src="https://img.shields.io/badge/Flow%20Team-9600F8?style=for-the-badge&link=https://flow.team/kr/index"></span>
</p>

## 화면 구성

## 주요 기능

## 아키텍처
