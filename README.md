# JAVA 암복호화 프로젝트 ✨

사용자 CI 나 주민번호 및 핸드폰 번호와 같은 개인정보는 DB에 저장할 때 뿐만아니라 조회되는 데이터도 암호화되어야 한다.
<br/>
<br/>

## 사용 스택
JAVA 11 <br/>
Spring boot 2 <br/>
MariaDB <br/>
JPA <br/>
<br/>

## 적용 기술
KISA SEED CBC <br/>
: 전자상거래, 금융, 무선통신 등에서 전송되는 개인정보와 같은 중요한 정보를 보호하기 위해 개발된 128비트 블록 암호 알고리즘<br/><br/>

외부 -> DB

BASE64(SEED128 CBC(평문)) 으로 암호화된 데이터를 SEED128 CBC로 복호화하고

복호화된 데이터를 AES로 암호화하여 DB에 저장

DB -> 외부

AES로 암호화된 DB의 데이터를 AES/CBC/PKCS5Padding 로 복호화하고

복호화된 데이터를 SEED128 CBC로 암호화여 외부에 노출

<br/>

## 구현 과정
https://seo-0.tistory.com/12
