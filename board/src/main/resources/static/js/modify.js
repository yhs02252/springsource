const actionForm = document.querySelector("#actionForm");

document.querySelector("#modify").addEventListener("submit", (e) => {
  e.preventDefault();
  if (confirm("수정을 완료 하시겠습니까?")) {
    e.target.submit();
  }
});

document.querySelector(".btn-outline-danger").addEventListener("click", (e) => {
  e.preventDefault();

  if (confirm("삭제 하시겠습니까?")) {
    actionForm.action = "/board/remove";
    actionForm.submit();
  }
});

document.querySelector(".secondary").addEventListener("click", (e) => {
  e.preventDefault();
  if (confirm("수정을 취소하고 목록으로 돌아가시겠습니까?")) {
    actionForm.method = "get"; // 매핑방식 변경
    actionForm.querySelector("[name='bno']").remove(); // 전체 리스트로 돌아가기 위해 프로토콜에서 primary key 조건 삭제
    actionForm.querySelector("[name='writerEmail']").remove();
    actionForm.action = "/board/list"; // action 을 /list path 매핑 controller 실행하도록 설정
    actionForm.submit(); // actionForm submit 및 action 실행
  }
});
