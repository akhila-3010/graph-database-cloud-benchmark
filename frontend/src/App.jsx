import { Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";
import Footer from "./components/Footer";

import Home from "./pages/Home";
import Connection from "./pages/Connection";
import Dataset from "./pages/Dataset";
import Verification from "./pages/Verification";
import Benchmark from "./pages/Benchmark";
import Report from "./pages/Report";
import NotFound from "./pages/NotFound";

function App() {
  return (
    <div className="min-h-screen flex flex-col">

      <Navbar />

      <main className="flex-grow pt-32 md:pt-24 bg-gray-50">

        <Routes>

          <Route path="/" element={<Home />} />

          <Route
            path="/connection"
            element={<Connection />}
          />

          <Route
            path="/dataset"
            element={<Dataset />}
          />

          <Route
            path="/verification"
            element={<Verification />}
          />

          <Route
            path="/benchmark"
            element={<Benchmark />}
          />

          <Route
            path="/report"
            element={<Report />}
          />

          <Route
            path="*"
            element={<NotFound />}
          />

        </Routes>

      </main>

      <Footer />

    </div>
  );
}

export default App;