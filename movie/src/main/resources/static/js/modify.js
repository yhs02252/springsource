const actionForm = document.querySelector("#actionForm");
const removeBtn = document.querySelector(".remove");
const listBtn = document.querySelector(".toList");

// uploadResult 안쪽 이미지 삭제 (DB 반영 X)
document.querySelector(".uploadResult").addEventListener("click", (e) => {
  if (e.target.tagName !== "I") return;

  // href 값 가져오기
  const element = e.target.closest("li");

  element.remove();

  e.stopPropagation();
});

listBtn.addEventListener("click", (e) => {
  e.preventDefault();

  if (confirm("수정을 취소하고 목록으로 돌아가시겠습니까?")) {
    actionForm.querySelector("[name='mno']").remove();
    actionForm.method = "get";
    actionForm.action = "/movie/list";
    actionForm.submit();
  }
});

if (removeBtn) {
  removeBtn.addEventListener("click", () => {
    if (confirm("해당 영화를 삭제하시겠습니까?")) {
      actionForm.action = "/movie/remove";
      actionForm.submit();
    }
  });
}
