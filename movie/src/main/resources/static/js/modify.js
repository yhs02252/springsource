const modifyForm = document.querySelector("#modifyFrom");

document.querySelector(".toList").addEventListener("click", (e) => {
  e.preventDefault();

  if (confirm("수정을 취소하고 목록으로 돌아가시겠습니까?")) {
    modifyForm.method = "get";
    modifyForm.action = "/movie/list";
    modifyForm.submit();
  }
});

if (modifyForm) {
  document.querySelector(".remove").addEventListener("click", (e) => {
    e.preventDefault();

    if (confirm("해당 영화를 삭제하시겠습니까?")) {
      modifyForm.action = "/movie/remove";
      modifyForm.submit();
    }
  });
}
