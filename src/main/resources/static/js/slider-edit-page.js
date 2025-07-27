const slides = document.querySelectorAll('.property-image');
console.log(slides);
const next = document.querySelector('.next');
const prev = document.querySelector('.prev');

let slideIndex = 0;
let intervalId = null;

function showSlide(index) {
    if (index >= slides.length) {
        slideIndex = 0;
    } else if (index < 0) {
        slideIndex = slides.length - 1;
    } else {
        slideIndex = index;
    }

    slides.forEach(slide => slide.classList.remove('displaySlide'));
    if (slides[slideIndex]) {
        slides[slideIndex].classList.add('displaySlide');
    }
}
function nextSlide() {
    clearInterval(intervalId);
    slideIndex++;
    showSlide(slideIndex);
    intervalId = setInterval(nextSlide, 5000);
}
function prevSlide() {
    clearInterval(intervalId);
    slideIndex--;
    showSlide(slideIndex);
    intervalId = setInterval(prevSlide, 5000);
}

if (slides.length > 0) {
    showSlide(slideIndex);
    intervalId = setInterval(nextSlide, 5000);
}

if(next) next.addEventListener('click', nextSlide);
if(prev) prev.addEventListener('click', prevSlide);