import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <div className="liquid-bg">
      <div className="liquid-blob red1"></div>
      <div className="liquid-blob red2"></div>
      <div className="liquid-blob red3"></div>
    </div>
    <App />
  </StrictMode>,
)
