const nicknameForm = document.querySelector("#nicknameForm");
const passwordForm = document.querySelector("#passwordForm");

nicknameForm.addEventListener("submit", (e) => {
  e.preventDefault();

  if (confirm("닉네임을 수정하시겠습니까?")) {
    e.target.submit();
  }
});

passwordForm.addEventListener("submit", (e) => {
  e.preventDefault();

  if (confirm("비밀번호를 수정하시겠습니까?")) {
    e.target.submit();
  }
});
