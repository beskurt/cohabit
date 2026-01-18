<script lang="ts">
	import HeaderFooter from '$lib/HeaderFooter.svelte';

	let email = '';
	let password = '';
	let error = '';
	let success = '';

	async function handleLogin() {
		error = '';
		success = '';

		try {
			const res = await fetch('http://localhost:8080/v1/login', {
				method: 'POST',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify({ email, password }),
			});

			if (!res.ok) {
				const message = await res.text();
				throw new Error(message);
			}

			const data = await res.json(); // { token: "..." }
			localStorage.setItem('auth_token', data.token);

			success = 'Login successful!';
			// Redirect or update UI
			window.location.href = '/';
		} catch (err) {
			error = `Login error: ${err.message}`;
		}
	}
</script>

<section class="form-section">
	<h2>Login</h2>
	<form on:submit|preventDefault={handleLogin}>
		<label for="email">Email</label>
		<input id="email" type="email" bind:value={email} required />

		<label for="password">Password</label>
		<input id="password" type="password" bind:value={password} required />

		<button type="submit">Login</button>
	</form>

	{#if error}
		<p style="color: red;">{error}</p>
	{/if}
	{#if success}
		<p style="color: green;">{success}</p>
	{/if}
</section>
