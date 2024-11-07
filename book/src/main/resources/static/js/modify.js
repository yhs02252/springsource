const actionFrom = document.querySelector("#actionForm");

document.querySelector(".btn-danger").addEventListener("click", (e) => {
  if (confirm("도서를 삭제하시겠습니까?")) {
    actionFrom.action = "/book/remove";
    actionFrom.submit();
  }
});
