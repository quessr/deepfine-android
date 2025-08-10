# To-Do App

Jetpack Compose 기반 단일 모듈 To-Do 앱입니다. Clean Architecture + MVI로 상태를 관리하고, Room으로 영속 저장합니다.
암호화(SQLCipher) 시도 코드는 별도 브랜치에 보관되어 있으며, 본 제출물(main)에는 미포함입니다.

## TL;DR

Tech: Jetpack Compose + Navigation, Clean Architecture + MVI, Room(암호화 미포함), Hilt, Kotlin Coroutines   
레이어: core / data / domain / feature  
암호화 브랜치: feature/sqlcipher


## 기능 & 검증 가이드 (요구사항 매핑)


1. **할 일 추가**
- 입력 → 우측 체크 버튼 → **목록 상단**에 추가
- 입력이 비어 있으면 **"할 일을 입력해주세요."** 표시 & 버튼 **비활성화**

2. **할 일 목록**
- 리스트 표시, **등록일 내림차순**

3. **데이터 유지**
- 앱 종료 후 재실행 시 **데이터 복원(Room)**
- *(암호화는 본 브랜치 미포함)*

4. **할 일 삭제**
- 항목 **왼쪽 스와이프 → “삭제” → 삭제**

5. **할 일 완료**
- 항목 **좌측 체크 버튼 → 목록에서 제거**
- 제거된 항목은 **History 화면**에 저장

6. **History**
- 메인 **우측 상단 버튼**으로 이동
- **등록일 + 완료일** 표시, **완료일 내림차순**

## 화면 구성
- Main (TodoList): 입력바 + 체크 버튼, 목록(스와이프 삭제/체크 완료), History 이동 버튼
- History: 완료 항목 카드(제목/등록일/완료일)

## 아키텍처 개요 (core / data / domain / feature)
-  **core**  
   앱 전반에서 재사용되는 공용 리소스 및 구성 요소

- **data**  
  실제 구현체 계층: 로컬/원격 데이터 소스 + Repository 구현
    - 구조:
        - `local/entity` — Room Entity 클래스
        - `local/dao` — DAO 인터페이스
        - `local/db` — Room Database 클래스
        - `mapper` — Entity ↔ Domain 모델 변환
        - `repository` — Repository 구현체 (`...Impl`)

- **domain**  
  비즈니스 규칙 계층: 순수 모델 / 인터페이스 / 유즈케이스
    - 구조:
        - `model` — 비즈니스 모델
        - `repository` — Repository 인터페이스
        - `usecase` — 기능 단위 유즈케이스

- **feature**  
  화면 단위(MVI): Contract / ViewModel / Screen 등 화면별 구성 요소

## 폴더 구조

```plaintext
com.quessr.deepfineandroid
├─ MainActivity.kt
├─ DeepfineAndroidApp.kt
├─ core/
│  ├─ di/                  # Hilt 모듈
│  └─ ui/theme
├─ data/
│  ├─ local/entity         # TodoEntity, HistoryEntity
│  ├─ local/dao            # TodoDao, HistoryDao
│  ├─ local/db             # DeepfineDatabase
│  ├─ mapper               # TodoMapper, HistoryMapper
│  └─ repository           # TodoRepositoryImpl, HistoryRepositoryImpl
├─ domain/
│  ├─ model                # Todo, History
│  ├─ repository           # 인터페이스
│  └─ usecase              # AddTodo, GetTodos, CompleteTodo, DeleteTodo, GetHistory
└─ feature/
   ├─ todo                 # TodoContract, TodoViewModel, TodoListScreen
   └─ history              # HistoryContract, HistoryViewModel, HistoryScreen
