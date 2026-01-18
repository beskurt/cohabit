<script lang="ts">
	import { onMount } from 'svelte';
	import { fetchListings, fetchCategories } from '$lib/api';

	let allListings = [];
	let categories = [];
	let search = '';
	let category = '';
	let condition = '';
	let minPrice: number | null = null;
	let maxPrice: number | null = null;

	onMount(async () => {
		allListings = await fetchListings();
		categories = await fetchCategories();
	});

	$: filtered = allListings
		.filter(l => l.title.toLowerCase().includes(search.toLowerCase()))
		.filter(l => category === '' || l.category.name.toLowerCase() === category.toLowerCase())
		.filter(l => condition === '' || l.condition.toLowerCase() === condition.toLowerCase())
		.filter(l => minPrice === null || l.price.amount >= minPrice)
		.filter(l => maxPrice === null || l.price.amount <= maxPrice);
</script>


<section class="form-section">
	<h2>Browse Listings</h2>
	<div style="display: flex; gap: 1rem; flex-wrap: wrap;">
		<input placeholder="Search..." bind:value={search} />

		<select bind:value={category}>
			<option value="">All Categories</option>
			{#each categories as c}
				<option value={c.name}>{c.name}</option>
			{/each}
		</select>

		<select bind:value={condition}>
			<option value="">All Conditions</option>
			<option value="NEW">New</option>
			<option value="USED">Used</option>
		</select>

		<input type="number" placeholder="Min Price" bind:value={minPrice} />
		<input type="number" placeholder="Max Price" bind:value={maxPrice} />
	</div>
</section>

<section class="featured">
	{#each filtered as product (product.id)}
		<div class="item">
			<a href={`/product/${product.id}`} style="text-decoration: none; color: inherit;">
<!--				<img src={product.images[0]?.url || '/fallback.jpg'} alt={product.title} />-->
				<div class="item-details">
					<h3>{product.title}</h3>
					<p>{product.price.amount.toFixed(2)} {product.price.currency}</p>
					<p>{product.condition}</p>
				</div>
			</a>
		</div>
	{/each}
</section>