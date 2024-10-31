document.querySelector("tbody").addEventListener("click", (e) => {
  // 완료 클릭하면 체크박스 value 가져오기
  // comForm 에 id value 값을 가져온 id 로 변경
  const chk = e.target;

  console.log(chk.value);
  console.log(chk.checked);

  const comform = document.querySelector("#comForm");
  comform.querySelector("[name='id']").value = chk.value;
  comform.querySelector("[name='completed']").value = chk.checked;

  console.log(comform.innerHTML);
  comform.submit();
});
