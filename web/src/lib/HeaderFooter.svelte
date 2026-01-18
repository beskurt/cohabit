<script>
	export let user = '';
	function handleLogout() {
		localStorage.removeItem('auth_token');
		window.location.href = '/login';
	}
</script>

<header>
	<h1>SofaKing</h1>
	<nav>
		<a href="/">Home</a>
		<a href="/browse">Browse</a>
		<a href="/sell">Sell</a>
		<a href="/login">Login</a>
		<a href="/register">Register</a>
		{#if user}
			<a href="/my-listings">My Listings</a>
			<button on:click={handleLogout}>Logout</button>
		{/if}
	</nav>
</header>

<main>
	<slot />
</main>

<footer>
	<p>&copy; 2025 SofaKing. All rights reserved.</p>
</footer>
