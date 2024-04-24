import React from "react";
import { Routes, Route } from "react-router-dom";
import AdminLogin from "./view/auth/admin/adminLogin";
import Login from "./view/auth/login";
import SignUp from "./view/auth/signUp";
import LandingPage from "./view/landingPage/landing";
import ResumeEdit from "./view/resumeEdit/resumeEdit";
import NotFound from "./view/error/notFound/notFound";

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/auth/signUp" element={<SignUp />} />
        <Route path="/auth/login" element={<Login />} />
        <Route path="/" element={<LandingPage />} />
        <Route path="/auth/admin/login" element={<AdminLogin />} />
        <Route path="/resumeEdit" element={<ResumeEdit />} />
        <Route element={<NotFound />} />
      </Routes>
    </div>
  );
}

export default App;
