# 👩🏻‍🌾 Danggn-Android


## 🥕 Github Convention

### 📍issue 생성
// issue 템플릿 링크 연결

### 📍Pull requests 생성
// pull requests 템플릿 링크 연결

### 📍commit message

    [feature] : 기능 추가, kotlin 작업

    [layout] : xml 작업

    [fix] : 에러 수정, 버그 수정

    [docs] : README, 문서

    [refactor] : 코드 리펙토링 (기능 변경 없이 코드만 수정할 때)

    [modify] : 코드 수정 (기능의 변화가 있을 때)

    [chore] : gradle 세팅, 위의 것 이외에 거의 모든 것

## 🥕 Code Convention

  ### **Naming Rule**
     btn_  :  버튼 id
     tv_   :  textView id
     iv_   :  imageView id
     et_   :  editText id
     rv_   :  recyclerVeiw id
     
  ### **Package**

  - 패키지의 이름은 항상 소문자로 하고, 밑줄을 사용하지 않기
  - 두 개 이상의 단어를 한 번에 사용하는 것을 지양하기

  ### **Class/Object**

  - Pascal Case

  ```kotlin
  class FillIn
  open class FillInParent { /* ... */ }
  object MoreSampleName : SampleName() { /* ... */ }
  ```

  ### **Function/Parameter/Variable**

  - Camel Case

  ```kotlin
  val initList = mutableList<WriteData>()
  fun getList: List<WriteData>() { /* ... */ }
  ```

  ### **Constant**

  - Upper Snake Case
  - 상수는 companion objet에 넣어 보관

  ```kotlin
  companion object {
      const val MAX_COUNT = 8
  }
  ```

  ### **Formatting**

  ### **Indenting**

  Tab 키를 써서 Indenting 하기

  ### **중괄호**

  괄호 뒤에 한 칸을 띄우고 사용하기
  ```kotlin
    if (elements != null) {
      for (element in elements) {
          // ...
      }
    }
  ```
  
## 🥕 Folder

     ┣ 📂util
     ┣ 📂network : 서버통신관련
     ┣ 📂data
     ┣ 📂presentation
        ┣ 📂home
          ┣ screens
          ┣ viewModels
        ┣ 📂write
          ┣ screens
          ┣ viewMdoels
        ┣ 📂read
          ┣ screens
          ┗ viewMdoels
          
 ## 🥕 Git branch Rule
 
    1) branch를 생성하기 전 issue 먼저 작성 (이때 issue는 템플릿 사용)
    2) issue 작성 후 생성되는 번호와 issue의 간략한 설명 등을 조합하여 branch의 이름을 결정 ("Prefix"/"Issue_Number" 의 양식)
    3) 각 issue에 맞는 작업 후 해당 브랜치는 main에 merge 진행
      
    main : 개발이 완료된 산출물이 저장될 공간
    develop : feature 브랜치에서 구현된 기능들이 merge될 브랜치
    feature : 기능을 개발하는 브랜치, 이슈별/작업별로 브랜치를 생성하여 기능을 개발
    refactor : 코드 리팩토링.수정하는 브랜치
    
 ## 🥕 Git flow 
 
![image](https://user-images.githubusercontent.com/62291759/169694035-2e3881e7-0de9-4203-a3f2-6d877a3b2aba.png)

    
    1) issue 번호 생성 (+ 브랜치 파기: 예시) feature/#이슈번호, fix/#이슈번호)

    2) 해당 issue에 맞게 작업 진행

    3) Add - Commit - Push - Pull Request 의 과정
    
      -> 팀원이 풀리퀘 올린 것이 있으면 코드리뷰 및 머지된 후 main에서 pull 받기

    4) 깃허브가서 풀리퀘 올리기

    5) 코드리뷰 진행 후 → main에 머지하기
