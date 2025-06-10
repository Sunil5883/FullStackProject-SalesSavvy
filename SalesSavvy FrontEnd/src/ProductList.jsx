import React from 'react';
import './assets/styles.css';

export function ProductList({ products, onAddToCart }) {
  if (products.length === 0) {
    return <p className="no-products">No products available.</p>;
  }

  return (
    <div className="product-list">
      <div className="product-grid">
        {products.map((products) => (
          <div key={products.productId} className="product-card">
            <img
              src={products.images[0]}
              alt={products.name}
              className="product-image"
              loading="lazy"
              onError={(e) => {
                e.target.src = 'https://unsplash.com/photos/abstract-blue-and-white-swirling-liquid-art-Y5PIyeEKXwk'; // Fallback image
              }}
            />
            <div className="product-info">
              <h3 className="product-name">{products.name}</h3>
              <p className="product-description">{products.description}</p>
              <p className="product-price">₹{products.price}</p>
              <button
                className="add-to-cart-btn"
                onClick={() => onAddToCart(products.productId)}
              >
                Add to Cart
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}