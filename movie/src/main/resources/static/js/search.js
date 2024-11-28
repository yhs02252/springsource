const searchForm = document.querySelector("#searchForm");

document.querySelector("[name='keyword']").addEventListener("keyup", (e) => {
  if (e.keyCode == 13) {
    // 검색어 입력 확인
    const keyword = e.target.value;

    // 없으면 메세지 띄우고 돌려보내기
    if (!keyword) {
      alert("키워드를 입력해 주세요");
      return;
    }
    // 있으면 keyword가져온 후
    // searchFrom 찾아서 keyword입력값을 변경
    searchForm.querySelector("[name='keyword']").value = keyword;

    // form submit
    searchForm.submit();
  }
});
