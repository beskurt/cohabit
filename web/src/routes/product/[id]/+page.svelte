<script lang="ts">
	import HeaderFooter from '$lib/HeaderFooter.svelte';
	import { page } from '$app/stores';
	import { onMount } from 'svelte';

	let product = null;

	onMount(async () => {
		const id = $page.params.id;
		const res = await fetch(`http://localhost:8081/v1/listings/${id}`);
		if (res.ok) product = await res.json();
	});

	async function handleBuy() {
		if (!product) return;

		const token = localStorage.getItem('auth_token');
		if (!token) {
			alert('Please log in.');
			window.location.href = '/login';
			return;
		}

		try {
			await fetch(`http://localhost:8081/v1/listings/${product.id}/buy`, {
				method: 'POST',
				headers: {
					Authorization: `Bearer ${token}`
				}
			});

			alert('Purchase successful!');
			window.location.href = '/browse';
		} catch (err) {
			console.error(err);
			alert('Purchase failed.');
		}
	}
</script>

{#if product}
	<section class="form-section">
		<a href="/browse" style="text-decoration: none; margin-bottom: 1rem; display: inline-block">← Back to browse</a>
<!--		<img src={product.images[0]?.url || '/fallback.jpg'} alt={product.title} style="width: 100%; max-height: 300px; object-fit: cover; border-radius: 8px; margin-bottom: 1rem;" />-->
		<h2>{product.title}</h2>
		<p><strong>Price:</strong> {product.price.amount.toFixed(2)} {product.price.currency}</p>
		<p><strong>Condition:</strong> {product.condition}</p>
		<p><strong>Category:</strong> {product.category.name}</p>
		<p>{product.description}</p>
		<button on:click={handleBuy} type="button">Buy Now</button>
	</section>
{:else}
	<p style="text-align: center; margin-top: 2rem;">Loading...</p>
{/if}