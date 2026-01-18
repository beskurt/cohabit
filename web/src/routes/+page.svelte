<script lang="ts">
	import { onMount } from 'svelte';
	import { fetchListings, type Listing } from '$lib/api';

	let featuredItems: Listing[] = [];

	onMount(async () => {
		const all = await fetchListings();
		featuredItems = [...all].sort((a, b) =>
			a.id.localeCompare(b.id)
		).slice(-3); // Get newest 4
	});
</script>

<section class="hero">
	<h2>Buy & Sell Secondhand Furniture, Royally</h2>
	<p>Discover hidden gems, list your pre-loved pieces, and furnish your space affordably with style.</p>
	<a href="/browse" class="btn">Start Browsing</a>
</section>

<section class="featured">
	{#each featuredItems as item (item.id)}
		<div class="item">
<!--			<img src={item.images[0]?.url} alt={item.title} />-->
			<div class="item-details">
				<h3>{item.title}</h3>
				<p>{item.price.amount.toFixed(2)} {item.price.currency}</p>
			</div>
		</div>
	{/each}
</section>