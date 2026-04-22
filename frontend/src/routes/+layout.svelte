<script>
  import { page } from '$app/stores';


   let { data, children } = $props();      
  let { user, isAuthenticated } = data;    

  const navItems = [
    { path: '/inventoryitem',                 label: 'Inventar',    icon: '◫' },
    { path: '/storage-locations', label: 'Lagerorte',   icon: '◎' },
    { path: '/recipes',          label: 'Rezepte',     icon: '◈' },
    { path: '/weekly-plan',      label: 'Wochenplan',  icon: '▦' },
    { path: '/statistics',       label: 'Statistiken', icon: '◻' },
  ];
</script>

<div class="app">
  <aside class="sidebar">
    <div class="logo">
      <div class="logo-title">SmartFoods</div>
      <div class="logo-sub">Deine digitale Küche</div>
    </div>


    <div class="user-section">
  {#if isAuthenticated}
    <span class="username">{user.name}</span>
    <form method="POST" action="/logout">
      <button type="submit" class="logout-btn">Logout</button>
    </form>
  {:else}
    <a href="/login" class="login-btn">Login</a>
    <a href="/signup" class="login-btn">Sign Up</a>
  {/if}
</div>

   <nav>
  {#if isAuthenticated}
    {#each navItems as item}
      <a href={item.path} class="nav-item" class:active={$page.url.pathname === item.path}>
        <span class="nav-icon">{item.icon}</span>
        {item.label}
      </a>
    {/each}

    {#if user.user_roles && user.user_roles.includes("admin")}
      <a href="/item-templates" class="nav-item" class:active={$page.url.pathname === '/item-templates'}>
        <span class="nav-icon">⚙</span>
        Templates
      </a>
    {/if}
  {/if}
</nav>
  </aside>

<main>
  {@render children()}
</main>
</div>

<style>
  .app {
    display: flex;
    height: 100vh;
    font-family: sans-serif;
  }
  .sidebar {
    width: 220px;
    background: #f8f8f8;
    border-right: 1px solid #e0e0e0;
    display: flex;
    flex-direction: column;
    padding: 20px 0;
  }
  .logo {
    padding: 0 20px 20px;
    border-bottom: 1px solid #e0e0e0;
    margin-bottom: 12px;
  }
  .logo-title { font-size: 18px; font-weight: 600; color: #3B6D11; }
  .logo-sub   { font-size: 12px; color: #888; }
  .nav-item {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 9px 20px;
    font-size: 14px;
    color: #555;
    text-decoration: none;
    border-left: 2px solid transparent;
  }


  .nav-item:hover { background: #fff; color: #222; }
  .nav-item.active {
    color: #3B6D11;
    background: #EAF3DE;
    border-left-color: #3B6D11;
    font-weight: 500;
  }
  .nav-icon { width: 18px; text-align: center; }
  main { flex: 1; overflow-y: auto; background: #f4f4f4; }
</style>