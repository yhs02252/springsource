// 영화 전체 리뷰 가져오기
const reviewLoaded = () => {
  fetch(`/reviews/${mno}/all`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
    });
};

reviewLoaded();

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
