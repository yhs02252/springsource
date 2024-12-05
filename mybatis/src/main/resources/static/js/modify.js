const actionFrom = document.querySelector("#actionForm");

document.querySelector(".btn-danger").addEventListener("click", (e) => {
  if (confirm("도서를 삭제하시겠습니까?")) {
    actionFrom.action = "/book/remove";
    actionFrom.submit();
  }
});

document.querySelector("#listButton").addEventListener("click", (e) => {
  // 목록 클릭 시 a 태그 기능 중지
  e.preventDefault();

  if (confirm("수정을 취소하고 목록으로 돌아가시겠습니까?")) {
    // actionform 에서 id요소 제거하기
    actionFrom.querySelector("[name='id']").remove();

    // actionform method는 get 변경
    actionFrom.method = "get";

    // actionform action은 list 변경
    actionFrom.action = "/book/bookList";

    // acttionform submit()
    actionFrom.submit();
  }
});
