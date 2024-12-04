document.querySelector(".uploadResult").addEventListener("click", (e) => {
  // a태그 기능 중지
  e.preventDefault();

  // 실제 x가 눌러진 태그 요소 찾기
  console.log("e.target(실제 이벤트 대상) : " + e.target);
  console.log("e.currentTarget(이벤트 대상의 부모) : " + e.currentTarget);

  // href 값 가져오기
  const element = e.target.closest("a");
  console.log(element);
  console.log(element.href);
  console.log(element.getAttribute("href"));

  // 이미지 삭제
  const removeDiv = e.target.closest("li");

  // 삭제할 이미지 경로 추출
  const filePath = element.getAttribute("href");

  let formData = new FormData();
  formData.append("filePath", filePath);

  fetch("/upload/remove", {
    method: "post",
    headers: {
      "X-CSRF-TOKEN": csrfValue,
    },
    body: formData,
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("에러 발생");
      }
      return response.text();
    })
    .then((data) => {
      console.log(data);
      if (data) {
        removeDiv.remove();
      }
    });
});
