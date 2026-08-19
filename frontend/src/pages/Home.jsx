import { Link } from "react-router-dom";
import { Database, Upload, BarChart3, ShieldCheck, Server, Code2 } from "lucide-react";

function Home() {
    return (
        <div className="min-h-screen bg-gradient-to-br from-blue-50 to-purple-100 p-5">

            <div className="max-w-6xl mx-auto">

                <div className="text-center mb-8">
                    <h1 className="text-4xl font-bold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent mb-3">
                        Graph Database Benchmark System
                    </h1>

                    <p className="text-gray-600 text-lg">
                        Benchmark graph databases by importing datasets, verifying graph structures,
                        and measuring query performance using different workloads.
                    </p>
                </div>


                <div className="bg-white shadow-lg rounded-xl p-5 mb-6">

                    <h2 className="text-2xl font-semibold mb-4">
                        🚀 Technologies Used
                    </h2>

                    <div className="grid grid-cols-2 md:grid-cols-3 gap-3">

                        <TechCard icon={<Code2 />} title="React + Vite" />
                        <TechCard icon={<Server />} title="Spring Boot" />
                        <TechCard icon={<Database />} title="Graph Database" />
                        <TechCard icon={<ShieldCheck />} title="Verification" />
                        <TechCard icon={<BarChart3 />} title="Benchmark Engine" />
                        <TechCard icon={<Upload />} title="Dataset Import" />

                    </div>

                </div>


                <h2 className="text-2xl font-semibold mb-4">
                    Quick Actions
                </h2>


                <div className="grid grid-cols-1 md:grid-cols-3 gap-4">

                    <ActionCard
                        to="/connection"
                        icon={<Database size={35} />}
                        title="Database Connection"
                        style="from-blue-500 to-blue-700"
                    />

                    <ActionCard
                        to="/dataset"
                        icon={<Upload size={35} />}
                        title="Import Dataset"
                        style="from-green-500 to-green-700"
                    />

                    <ActionCard
                        to="/benchmark"
                        icon={<BarChart3 size={35} />}
                        title="Run Benchmark"
                        style="from-purple-500 to-purple-700"
                    />

                </div>

            </div>

        </div>
    );
}


function TechCard({ icon, title }) {
    return (
        <div className="flex items-center gap-3 bg-gray-50 rounded-lg p-4 hover:shadow-md transition">

            <div className="text-blue-600">
                {icon}
            </div>

            <span className="font-medium text-gray-700">
                {title}
            </span>

        </div>
    );
}


function ActionCard({ to, icon, title, style }) {
    return (
        <Link
            to={to}
            className={`bg-gradient-to-r ${style} text-white rounded-xl p-6 shadow-lg hover:scale-105 transition flex flex-col items-center gap-3`}
        >

            {icon}

            <h3 className="text-lg font-semibold">
                {title}
            </h3>

        </Link>
    );
}


export default Home;