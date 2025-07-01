// ============= Bootstrap JS //
//<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js"></script>

// ========== Sticky Header Scroll ========== //
// Sticky header background change on scroll
window.addEventListener("scroll", function () {
  const header = document.getElementById("mainHeader");

  if (window.scrollY > 50) {
    header.classList.add("scrolled");
  } else {
    header.classList.remove("scrolled");
  }
});



