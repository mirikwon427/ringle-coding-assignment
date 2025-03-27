# 📚 Ringle 튜터링 예약 시스템

본 프로젝트는 온라인 튜터링 서비스를 위한 스케줄 관리 및 수업 예약 시스템으로, **Spring Boot**, **Spring Data JPA**, **MySQL**, **Lombok** 등을 사용하여 구현하였습니다. 시간 데이터를 **UTC 기준**으로 통일하여 관리하며, 20분 및 40분 길이의 수업 예약을 지원합니다.

---

## 🗂️ 목차

1. [프로젝트 개요](#-프로젝트-개요)
2. [기술 스택](#-기술-스택)
3. [엔티티 상세 설명 및 관계](#-엔티티-상세-설명-및-관계)
4. [설계의 배경](#-설계의-배경)
5. [프로젝트 실행 방법](#-프로젝트-실행-방법)
6. [API 사용 방법 (테스트 방법 포함)](#-api-사용-방법-테스트-방법-포함)
7. [추가 참고사항](#-추가-참고사항)

---

## 🎯 프로젝트 개요

이 시스템의 주요 목적은 사용자(학생)가 원하는 튜터와 수업을 선택하여 간편하게 예약 및 관리할 수 있도록 돕는 것입니다. 사용자는 원하는 수업 패키지를 구매한 후, 해당 패키지 내에서 수업을 예약하고 관리할 수 있습니다.

---

## 🛠️ 기술 스택

- **백엔드:** Java 17, Spring Boot, Spring Data JPA
- **데이터베이스:** MySQL
- **빌드 도구:** Gradle
- **시간 관리:** UTC 기준 (`Instant`사용)

---

## 📌 엔티티 상세 설명 및 관계

본 시스템은 다음과 같은 엔티티들로 구성되어 있습니다.

### 🔸 User (사용자)
- 학생(사용자)의 정보를 관리합니다.

### 🔸 Tutor (튜터)
- 튜터의 이름, 이메일, 전화번호, 대학교, 전공, 상태 및 시간대 정보를 저장합니다.

### 🔸 ClassTime (수업 시간 슬롯)
- 수업이 가능한 개별 시간을 관리하며, 시작 시간과 가용성 여부를 저장합니다.

### 🔸 TutorSchedule (튜터별 스케줄)
- 튜터와 특정 수업 시간(`ClassTime`) 간의 연결을 관리합니다. 
- 각 튜터가 어떤 시간에 수업을 진행 가능한지 저장하며, 각 스케줄의 가용성을 관리합니다.

**관계:**  
- `TutorSchedule` ↔️ `Tutor` (**다대일**): 한 명의 튜터는 여러 개의 스케줄을 가질 수 있습니다.  
- `TutorSchedule` ↔️ `ClassTime` (**다대일**): 하나의 시간은 여러 튜터 스케줄에서 사용 가능합니다.

### 🔸 Booking (예약)
- 사용자가 특정 튜터 스케줄을 예약한 정보를 저장합니다.
- 예약 시간, 수업 길이(분), 예약 상태(`confirmed`, `canceled`) 등을 관리합니다.

**관계:**  
- `Booking` ↔️ `User` (**다대일**): 한 사용자는 여러 예약을 가질 수 있습니다.
- `Booking` ↔️ `TutorSchedule` (**일대일**, One-to-One):
  하나의 튜터 스케줄은 반드시 한 명의 사용자만 예약할 수 있으며, 한 번 예약되면 더 이상 예약할 수 없습니다.

### 🔸 LessonPackage (수업 패키지)
- 사용자가 구매 가능한 수업 패키지를 정의합니다. (예: 20분 수업 4회, 28일간 유효 등)

### 🔸 UserLesson (사용자가 구매한 수업)
- 사용자가 구매한 패키지 정보를 저장하며, 구매일, 시작일, 종료일, 잔여 수업 횟수 및 상태(`active`, `inactive`) 등을 관리합니다.

**관계:**  
- `UserLesson` ↔️ `User` (**다대일**): 한 사용자는 여러 개의 수업 패키지를 구매할 수 있습니다.
- `UserLesson` ↔️ `LessonPackage` (**다대일**): 하나의 수업 패키지는 여러 사용자에게 판매될 수 있습니다.

---

## 📖 설계의 배경

### ⏰ 시간 관리
- 모든 시간 데이터를 UTC 기준으로 통일하여 관리하여, 시간대 변환의 혼란을 방지하였습니다.
- 데이터베이스와 애플리케이션에서 사용하는 모든 시간 타입은 `Instant`를 활용합니다.

### 📝 수업 예약 로직
- **20분 수업:** 선택한 수업의 시간이 예약 가능할 경우 예약 가능
- **40분 수업:** 선택한 수업의 시간과 연속된 다음 수업의 시간이 모두 예약 가능해야 예약 가능

### 📐 API 응답 설계
- API 응답 데이터를 플랫(flat)한 형태로 제공하여 클라이언트에서 간편하게 데이터를 사용할 수 있도록 했습니다.

---

## 🚀 프로젝트 실행 방법

### 1️⃣ 사전 준비 사항
- **Java:** Java 17 이상
- **데이터베이스:** MySQL
- **Git**

### 2️⃣ 프로젝트 빌드 및 실행

**저장소 클론 및 브랜치 체크아웃**
```bash
git clone https://github.com/mirikwon427/ringle-coding-assignment.git
cd ringle-coding-assignment
git checkout develop
```
---

## 🔗 API 사용 방법 (테스트 방법 포함)

> API를 테스트하는 방법을 자세히 안내합니다.  
> [➡️ **API 테스트 방법 보러가기**](https://marbled-perfume-400.notion.site/API-1c3f704966e480c481f2d3206d694056)

---

## 📌 추가 참고사항

### 🔖 ERD (Entity Relationship Diagram)

아래는 본 시스템의 엔티티 관계를 나타낸 ERD입니다.

![Ringle 튜터링 시스템 ERD](src/main/resources/static/ringle.png)

### 🚨 예외 처리

- API 호출 시 발생하는 예외는 적절한 메시지와 함께 반환됩니다. 오류 발생 시 애플리케이션 로그를 확인해주세요.

---

🎉 **Ringle 튜터링 예약 시스템**을 활용하여 간편하게 튜터링 예약을 관리하세요!

