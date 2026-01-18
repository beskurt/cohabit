export interface Listing {
	id: string;
	title: string;
	description: string;
	category: { name: string };
	condition: string;
	price: { amount: number; currency: string };
	images: { url: string; displayOrder: number }[];
	seller: { userId: string; email: string | null };
}

export async function fetchListings(): Promise<Listing[]> {
	const res = await fetch('http://localhost:8081/v1/listings');
	if (!res.ok) throw new Error('Failed to fetch listings');
	return await res.json();
}

export type Category = {
	name: string;
};

export async function fetchCategories(): Promise<Category[]> {
	const res = await fetch('http://localhost:8081/v1/categories');
	if (!res.ok) {
		throw new Error('Failed to fetch categories');
	}
	return res.json();
}