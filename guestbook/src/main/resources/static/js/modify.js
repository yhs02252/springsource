const actionForm = document.querySelector("#actionForm");

document.querySelector(".btn-outline-danger").addEventListener("click", (e) => {
  e.preventDefault();

  if (confirm("삭제 하시겠습니까?")) {
    actionForm.action = "/guestbook/remove";
    actionForm.submit();
  }
});

document.querySelector(".secondary").addEventListener("click", (e) => {
  e.preventDefault();
  if (confirm("수정을 취소하고 목록으로 돌아가시겠습니까?")) {
    actionForm.method = "get"; // 매핑방식 변경
    actionForm.querySelector("[name='gno']").remove(); // 전체 리스트로 돌아가기 위해 프로토콜에서 primary key 조건 삭제
    actionForm.action = "/guestbook/list"; // action 을 /list path 매핑 controller 실행하도록 설정
    actionForm.submit(); // actionForm submit 및 action 실행
  }
});
