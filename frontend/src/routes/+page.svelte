<script>
  let { data } = $props();
  let { isAuthenticated } = data;
</script>

{#if isAuthenticated}
  <h1>Welcome to Freelancer4U!</h1>
  <!-- Beispiel für die Verwendung von Bildern im Ordner 'static/images' -->
  <img src="/images/vite.svg" alt="Vite Logo" />
{:else}
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-header">Welcome to Freelancer4U</div>
          <div class="card-body text-center">
            <h5 class="card-title">Get Started</h5>
            <p class="card-text">
              Please log in to access your account or sign up to create a new
              one.
            </p>
            <a href="/login" class="btn btn-primary me-2">Login</a>
            <a href="/signup" class="btn btn-outline-primary">Sign Up</a>
          </div>
        </div>
      </div>
    </div>
  </div>
{/if}
