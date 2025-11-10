<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Yumijo README</title>
<style>
  body { font-family: Arial, sans-serif; line-height: 1.6; background-color: #f9f9f9; padding: 20px; color: #333; }
  h1, h2 { color: #ff5722; }
  a { color: #ff5722; text-decoration: none; }
  a:hover { text-decoration: underline; }
  .section { margin-bottom: 40px; }
  .flex-container { display: flex; flex-wrap: wrap; gap: 20px; }
  .card { 
    width: 180px; background: #fff; padding: 15px; border-radius: 12px; 
    box-shadow: 0 4px 8px rgba(0,0,0,0.1); text-align: center; transition: transform 0.2s, box-shadow 0.2s; cursor: pointer;
  }
  .card:hover { transform: scale(1.05); box-shadow: 0 8px 16px rgba(0,0,0,0.2); }
  .card-icon { font-size: 40px; margin-bottom: 10px; }
  .card-title { font-weight: bold; margin-bottom: 5px; }
  .card-desc { font-size: 13px; color: #555; }
</style>
</head>
<body>

<h1>🍱 Yumijo</h1>
<p>Welcome to <strong>Yumijo</strong>, your ultimate shopping & food experience app! A modern, sleek Android app built with care and passion for learning and improving every day. 🚀</p>

<div class="section">
  <h2>📌 Table of Contents</h2>
  <ul>
    <li><a href="#features">Features</a></li>
    <li><a href="#technologies">Technologies</a></li>
  </ul>
</div>

<div class="section" id="features">
  <h2>✨ Features</h2>
  <div class="flex-container">
    <div class="card">
      <div class="card-icon">🛍️</div>
      <div class="card-title">Product Catalog</div>
      <div class="card-desc">Browse and search through a wide range of products with rich details.</div>
    </div>
    <div class="card">
      <div class="card-icon">❤️</div>
      <div class="card-title">Favorites</div>
      <div class="card-desc">Save your favorite items locally with Room database for offline access.</div>
    </div>
    <div class="card">
      <div class="card-icon">🌐</div>
      <div class="card-title">API Integration</div>
      <div class="card-desc">Fetch products and data dynamically using Retrofit REST APIs.</div>
    </div>
    <div class="card">
      <div class="card-icon">🔥</div>
      <div class="card-title">Cloud Sync</div>
      <div class="card-desc">Keep user data safe and synchronized with Firestore.</div>
    </div>
    <div class="card">
      <div class="card-icon">🖼️</div>
      <div class="card-title">Images</div>
      <div class="card-desc">Efficiently load images with Glide for smooth UI experience.</div>
    </div>
  </div>
</div>

<div class="section" id="technologies">
  <h2>🛠️ Technologies</h2>
  <div class="flex-container">
    <div class="card">
      <div class="card-icon">🏗️</div>
      <div class="card-title">Android XML</div>
      <div class="card-desc">Designing layouts and UI components efficiently.</div>
    </div>
    <div class="card">
      <div class="card-icon">☕</div>
      <div class="card-title">Kotlin</div>
      <div class="card-desc">Main language for app logic and functionality.</div>
    </div>
    <div class="card">
      <div class="card-icon">🌐</div>
      <div class="card-title">Retrofit</div>
      <div class="card-desc">Fetching and sending data with REST APIs.</div>
    </div>
    <div class="card">
      <div class="card-icon">🗃️</div>
      <div class="card-title">Room Database</div>
      <div class="card-desc">Safely store offline favorites and user ratings.</div>
    </div>
    <div class="card">
      <div class="card-icon">🔥</div>
      <div class="card-title">Firestore</div>
      <div class="card-desc">Securely keep user data synced in the cloud.</div>
    </div>
    <div class="card">
      <div class="card-icon">🖼️</div>
      <div class="card-title">Glide</div>
      <div class="card-desc">Efficient image loading for smoother UI.</div>
    </div>
    <div class="card">
      <div class="card-icon">🧩</div>
      <div class="card-title">Data Binding</div>
      <div class="card-desc">Seamless binding between XML layouts and Kotlin code.</div>
    </div>
    <div class="card">
      <div class="card-icon">🔔</div>
      <div class="card-title">Snackbar & AlertDialog</div>
      <div class="card-desc">Interactive notifications and confirmation dialogs.</div>
    </div>
    <div class="card">
      <div class="card-icon">🔄</div>
      <div class="card-title">Shared ViewModel</div>
      <div class="card-desc">Smooth data sharing between fragments.</div>
    </div>
  </div>
</div>

</body>
</html>
