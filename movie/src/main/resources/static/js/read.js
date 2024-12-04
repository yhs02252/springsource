const reviewForm = document.querySelector("#reviewForm");
const reviewList = document.querySelector(".review-list");

// 날짜 포멧
const formatDate = (str) => {
  const date = new Date(str);

  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    "/ " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};

// 영화 전체 리뷰 가져오기
const reviewLoaded = () => {
  fetch(`/reviews/${mno}/all`)
    .then((response) => {
      if (!response.ok) throw new Error("주소 확인");
      return response.json();
    })
    .then((data) => {
      console.log(data);

      document.querySelector(".review-cnt").innerHTML = data.length;

      if (data.length > 0) {
        reviewList.classList.remove("hidden");
      }

      let result = "";
      data.forEach((review) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom py-2 review-row" data-rno="${review.reviewNo}">`;
        result += `<div class="flex-grow-1 align-self-center"><div><span class="text-gray-600 font-semibold">${review.text}</span></div>`;
        result += `<div><span class="d-inline-block mr-3">${review.memberNickname}</span>평점 : <span class="grade">${review.grade}</span><div class="starrr"></div></div>`;
        result += `<div class="text-muted"><span class="small">${formatDate(
          review.regDate
        )}</span></div></div>`;
        if (review.memberEmail === loginUser) {
          result += `<div class="d-flex flex-column align-self-center"><div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
          result += `<div class="mb-2"><button class="btn btn-outline-success btn-sm">수정</button></div></div>`;
        }
        result += `</div>`;
      });
      reviewList.innerHTML = result;
    });
};

reviewLoaded();

const reviewNoHidden = reviewForm.querySelector("#reviewNo");
const memberIdHidden = reviewForm.querySelector("#memberId");
const memberEamilHidden = reviewForm.querySelector("#memberEmail");
const nickname = reviewForm.querySelector("[name='nickname']");
const text = reviewForm.querySelector("[name='text']");

// 리뷰 작성 / 수정
reviewForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const review = {
    reviewNo: reviewNoHidden.value,
    text: text.value,
    movieNo: mno,
    grade: grade || 0,
    memberId: memberIdHidden.value,
    memberEmail: memberEamilHidden.value,
    memberNickname: nickname.value,
  };

  if (!reviewNoHidden.value) {
    if (confirm("리뷰를 등록 하시겠습니까?")) {
      fetch(`/reviews/${mno}/new`, {
        headers: {
          "content-type": "application/json",
          "X-CSRF-TOKEN": csrfValue,
        },
        body: JSON.stringify(review),
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
            text.value = "";

            alert(data + "번 리뷰 작성 완료!");
            reviewLoaded();
          }
        });
    }
  } else {
    fetch(`/reviews/${mno}/${reviewNoHidden.value}`, {
      headers: {
        "content-type": "application/json",
        "X-CSRF-TOKEN": csrfValue,
      },
      body: JSON.stringify(review),
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
          nickname.value = "";
          text.value = "";
          memberEamilHidden.value = "";
          reviewNoHidden.value = "";

          reviewForm
            .querySelector(".starrr a:nth-child(" + grade + ")")
            .click();

          alert(data + "번 리뷰 수정완료!");
          reviewForm.querySelector(".btn-outline-danger").innerHTML =
            "리뷰 등록";

          reviewLoaded();
        }
      });
  }
});

// 리뷰 삭제 / 조회
// 삭제버튼 클릭 시 reviewNo 가져오기
reviewList.addEventListener("click", (e) => {
  // 실제 이벤트 발생 요소는 누구인가?
  console.log(e.target);
  const btn = e.target;

  // 이벤트가 발생한 버튼이 속한 data-rno 를 가지고 있는 부모 태그 찾아오기
  console.log(btn.closest(".review-row"));

  // data-rno 값을 가져오기
  const reviewNo = btn.closest(".review-row").dataset.rno;

  // 작성자 email
  // const memberEmail = reviewForm.memberEmail.value;
  const form = new FormData();
  form.append("memberEmail", memberEamilHidden.value);

  // 클래스명 : classList
  if (btn.classList.contains("btn-outline-danger")) {
    // 리뷰 삭제
    if (confirm("이 리뷰를 삭제하시겠습니까?")) {
      fetch(`/reviews/${mno}/${reviewNo}`, {
        headers: {
          "X-CSRF-TOKEN": csrfValue,
        },
        body: form,
        // body: JSON.stringify(mno),
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

          alert(data + "번 리뷰 삭제");
          reviewLoaded();
        });
    }
  } else if (btn.classList.contains("btn-outline-success")) {
    // 리뷰 조회
    fetch(`/reviews/${mno}/${reviewNo}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error("rno 에러 발생");
        }
        return response.json();
      })
      .then((data) => {
        console.log(data);

        // name 값으로도 찾을 수 있음
        // reviewForm.reviewNo.value
        reviewNoHidden.value = data.reviewNo;
        memberEamilHidden.value = data.memberEmail;
        nickname.value = data.memberNickname;
        reviewForm.querySelector("[name='text']").value = data.text;
        nickname.classList.add("readonly");

        reviewForm
          .querySelector(".starrr a:nth-child(" + data.grade + ")")
          .click();

        reviewForm.querySelector(".btn-outline-danger").innerHTML = "리뷰 수정";
      });
  }
});

// 이미지 모달 요소 가져오기
const imgModal = document.querySelector("#imgModal");

if (imgModal) {
  imgModal.addEventListener("show.bs.modal", (e) => {
    // modal 띄우는 img 요소 가져오기
    const posterImg = e.relatedTarget;

    // data- 가져오기
    const file = posterImg.getAttribute("data-file");
    console.log(file);

    imgModal.querySelector(".modal-title").textContent = `${title}`;

    imgModal.querySelector(
      ".modal-body"
    ).innerHTML = `<img src="/upload/display?fileName=${file}" alt="" style="width:100%">`;
  });
}
