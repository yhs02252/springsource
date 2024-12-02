const nicknameForm = document.querySelector("#nicknameForm");

nicknameForm.addEventListener("submit", (e) => {
  e.preventDefault();

  if (confirm("닉네임을 수정하시겠습니까?")) {
    e.target.submit();
  }
});
