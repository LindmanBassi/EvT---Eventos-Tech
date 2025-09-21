import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import AuthPage from './pages/AuthPage';
import EventosPage from './pages/EventosPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<AuthPage />} />
        <Route path="/eventos" element={<EventosPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
