// 전체 댓글 리스트
const replyList = document.querySelector(".reply-list");

// 날짜 처리 함수
const formatDateTime = (str) => {
  const date = new Date(str);

  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};

// 페이지가 로드되면 현재 bno의 댓글 가져오기
const replyLoaded = () => {
  fetch(`/replies/board/${bno}`)
    .then((response) => {
      if (!response.ok) throw new Error("주소 확인");
      return response.json();
    })
    .then((data) => {
      console.log(data);

      //   전체 댓글 수 표시
      document.querySelector(".d-inline-block").innerHTML = data.length;

      let result = "";
      data.forEach((reply) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno="${reply.rno}">`;
        result += `<div class="p-3"><img src="/img/default.png" alt="" class="rounded-circle mx-auto d-block" style="width: 60px; height: 60px" /></div>`;
        result += `<div class="flex-grow-1 align-self-center">`;
        result += `<span>${reply.replyer}</span>`;
        result += `<div><span class="fs-5">${reply.text}</span></div>`;
        result += `<div class="text-muted"><span class="small">${formatDateTime(
          reply.regDate
        )}</span></div></div>`;
        result += `<div class="d-flex flex-column align-self-center"><div class="mb-2">`;
        result += `<button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div></div>`;
        result += `</div>`;
      });
      replyList.innerHTML = result;
    });
};

replyLoaded();

// 댓글 작성
// replyForm submit 되면 이벤트 발생
// submit 중지
// replyForm 안에 있는 replyer, text value 가져와서 변수에 담기
const replyForm = document.querySelector("#replyForm");

replyForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const replyer = replyForm.querySelector("#replyer");
  const text = replyForm.querySelector("#text");
  const rno = replyForm.querySelector("#rno");

  // 자바 스크립트 객체 생성
  const reply = {
    text: text.value,
    replyer: replyer.value,
    bno: bno,
  };

  // JSON.stringify(객체) => json 변환

  if (!rno.value) {
    // 새 댓글
    fetch(`/replies/new`, {
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify(reply),
      method: "post",
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
          // 댓글 폼에 남아있는 내용 제거하기
          replyer.value = "";
          text.value = "";
          // data 번 댓글이 등록 되었습니다 (alert)
          alert(data + "번 댓글 등록되었습니다");
          // 댓글 추가
          replyLoaded();
        }
      });
  } else {
    // 수정 댓글
    fetch(`/replies/${rno.value}`, {
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify(reply),
      method: "put",
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
          // 댓글 폼에 남아있는 내용 제거하기
          replyer.value = "";
          text.value = "";
          rno.value = "";
          // data 번 댓글이 수정 되었습니다 (alert)
          alert(data + "번 댓글이 수정되었습니다");
          // 댓글 추가
          replyLoaded();
        }
      });
  }
});

// 댓글 수정
// 수정 버튼 누르면 해당 댓글이 replyForm 안에 보여주기

// 이벤트 전파를 이용해서 이벤트 감지(부모)
replyList.addEventListener("click", (e) => {
  // 실제 이벤트 발생 요소는 누구인가?
  console.log(e.target);
  const btn = e.target;

  // 이벤트가 발생한 버튼이 속한 data-rno 를 가지고 있는 부모 태그 찾아오기
  console.log(btn.closest(".reply-row"));

  // data-rno 값을 가져오기
  const rno = btn.closest(".reply-row").dataset.rno;
  // 클래스명 : classList
  if (btn.classList.contains("btn-outline-danger")) {
    fetch(`/replies/${rno}`, {
      method: "delete",
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("삭제 안됨");
        }
        return response.text();
      })
      .then((data) => {
        console.log("data 확인 : " + data);

        alert(data + "번 댓글이 삭제되었습니다");
      });
  } else if (btn.classList.contains("btn-outline-success")) {
    fetch(`/replies/${rno}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error("rno 에러 발생");
        }
        return response.json();
        // response => response.ok
        // {} 중괄호가 없다면 return 생략 가능
      })
      .then((data) => {
        console.log(data);
        // 해당 댓글을 replyForm 안에 보여주기
        replyForm.querySelector("#rno").value = data.rno;
        replyForm.querySelector("#replyer").value = data.replyer;
        replyForm.querySelector("#text").value = data.text;
      });
  }
});
// 수정 버튼 클릭시 rno 가져오기
