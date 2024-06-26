## 요구사항
1. **유저 관리**
   - 회원가입 기능을 통해 사용자는 계정을 생성할 수 있습니다.
     - [ ] 이메일 인증을 통한 회원가입 기능을 만든다.
     - [ ]  개인정보, 이름, 비밀번호, 주소, 이메일은 암호화하여 저장한다.
     - [ ]  필수 요소: 이름, 전화번호, 주소
   - 로그인 및 로그아웃 기능을 통해 사용자는 편리하게 서비스를 이용할 수 있습니다.
       - [ ]  jwt 토큰을 활용한 로그인 기능을 만든다.
       - [ ]  로그아웃 기능을 제공한다.
   - 마이페이지를 통해 사용자는 자신의 정보를 업데이트할 수 있습니다.
       - [ ]  주소, 전화번호를 업데이트 할 수 있다.
       - [ ]  비밀번호를 업데이트 할 수 있다.

2. **상품**
    - 등록되어 있는 상품의 리스트를 보여주고 사용자가 구매할 수 있는 인터페이스를 제공합니다.
        - [ ]  기능 구현 완료
    - 상품을 클릭시 상품의 상세 정보를 제공해야합니다.
        - [ ]  기능 구현 완료
    - 상품의 재고 관리를 위한 유저 인터페이스는 별도로 구현하지 않습니다.


3. **주문**
    - 사용자는 노출된 상품에 한하여 주문 및 WishList에 등록 할 수 있습니다.
        - 1주차에서는 재고 부족으로 인한 주문 불가는 없다고 가정 합니다. 추후 구현 예정
        - [ ]  기능 구현 완료
    - 마이페이지를 통해 WishList에 등록한 상품과 주문한 상품의 상태를 조회 할 수 있습니다.
        - [ ]  WishList에서는 내가 등록한 상품에 대한 정보를 보여주고, 아래의 기능을 제공합니다.
            - [ ]  제품의 상세 페이지로 이동
            - [ ]  상품의 수량 변경 및 주문
            - [ ]  위시리스트 내 항목의 수정
        - [ ]  주문내역에서는 사용자가 주문한 상품에 대한 상태를 보여주고 상품에 대한 주문 취소, 반품 기능을 제공합니다.(주문 내역중 일부 상품에 대한 취소및 반품은 없다고 가정합니다.)
            - [ ]  주문 상품에 대한 상태 조회(주문 후 D+1에 배송중, D+2일에 배송완료가 됩니다.)
            - [ ]  주문 상품에 대한 취소
                - [ ]  주문 상태가 배송중이 되기 이전까지만 취소가 가능하며 취소 후에 는 상품의 재고를 복구 되어야 합니다 주문 취소후 상태는 취소완료로 변경 됩니다.
            - [ ]  상품에 대한 반품
                - [ ]  배송 완료 후 D+1일까지만 반품이 가능하고 그이후에는 반품이 불가능 합니다.
                - [ ]  배송 완료가 된 상품에 대해서만 반품이 가능하며 반품한 상품은 반품 신청 후 D+1에 재고에 반영 됩니다. 재고에 반영된후 상태는 반품완료로 변경됩니다.

4. **추가 구현 내용**
   - [ ]  **모든 기기에서 로그아웃 기능을 제공한다.**
   - [ ]  **비밀 번호 업데이트 시 모든 기기에서 로그아웃 되어야 한다.**
   - [ ]  이메일을 통한 2차 로그인 인증 기능을 제공합니다.