<script>
    let { form } = $props();
</script>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">Login</div>
                <div class="card-body">
                    {#if form?.error}
                        <div class="alert alert-danger" role="alert">
                            {form.error}
                        </div>
                    {/if}
                    <form method="POST" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label for="email" class="form-label">E-Mail</label>
                            <input
                                type="email"
                                class="form-control"
                                id="email"
                                name="email"
                                required
                            />
                            <div class="invalid-feedback">
                                Please provide your e-mail address.
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input
                                type="password"
                                class="form-control"
                                id="password"
                                name="password"
                                required
                            />
                            <div class="invalid-feedback">
                                Please provide your password.
                            </div>
                        </div>
                        <div class="row align-items-end">
                            <div class="col">
                                <button type="submit" class="btn btn-primary">Log in</button>
                            </div>
                            <div class="col-auto">
                                <a href="/signup">Sign up</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
