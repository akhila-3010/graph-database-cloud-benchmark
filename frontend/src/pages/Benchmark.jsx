import { useState } from "react";
import {
    BarChart3,
    Activity,
    Timer,
    Gauge,
    LoaderCircle
} from "lucide-react";
import api from "../services/api";

function Benchmark() {

    const [results, setResults] = useState([]);

    const [loading, setLoading] = useState(false);

    const runBenchmark = async () => {

        try {

            setLoading(true);

            setResults([]);

            const response =
                    await api.get("/benchmark/run");

            setResults(
                    response.data.results
            );

        }

        catch (error) {

            alert(
                "Unable to run benchmark."
            );

        }

        finally {

            setLoading(false);

        }

    };

    const averageLatency =
            results.length > 0
            ?
            (
                results.reduce(
                    (sum, item) =>
                    sum + item.averageLatency,
                    0
                ) / results.length
            ).toFixed(2)
            :
            "0";

    const averageThroughput =
            results.length > 0
            ?
            (
                results.reduce(
                    (sum, item) =>
                    sum + item.throughput,
                    0
                ) / results.length
            ).toFixed(3)
            :
            "0";

    return (

        <div className="min-h-screen bg-gray-50 p-6">

            <div className="max-w-7xl mx-auto">

                <h1 className="text-3xl font-bold mb-6">

                    Graph Database Benchmark

                </h1>

                <div className="bg-white shadow-lg rounded-xl p-6 mb-6">

                    <div className="flex items-center gap-3 mb-6">

                        <BarChart3
                            className="text-purple-600"
                            size={36}
                        />

                        <div>

                            <h2 className="text-xl font-semibold">

                                Benchmark Performance

                            </h2>

                            <p className="text-gray-500 text-sm">

                                Execute all benchmark workloads and analyze graph database performance.

                            </p>

                        </div>

                    </div>

                    <button
                        onClick={runBenchmark}
                        disabled={loading}
                        className="
                        bg-purple-600
                        hover:bg-purple-700
                        disabled:bg-gray-400
                        text-white
                        px-6
                        py-2
                        rounded-lg
                        flex
                        items-center
                        gap-2
                        transition
                        "
                    >

                        {
                            loading &&

                            <LoaderCircle
                                className="animate-spin"
                                size={18}
                            />

                        }

                        {
                            loading
                            ?
                            "Running Benchmark..."
                            :
                            "Run Benchmark"
                        }

                    </button>

                    {
                        loading &&

                        <p className="text-sm text-gray-500 mt-3">

                            Running benchmark workloads...

                        </p>

                    }

                </div>

                {
                    results.length > 0 &&

                    <>

                        <div className="
                        grid
                        grid-cols-1
                        md:grid-cols-3
                        gap-5
                        mb-6
                        ">

                            <SummaryCard
                                icon={
                                    <Activity size={30}/>
                                }
                                title="Benchmarks"
                                value={results.length}
                                color="text-blue-600"
                            />

                            <SummaryCard
                                icon={
                                    <Timer size={30}/>
                                }
                                title="Average Latency"
                                value={`${averageLatency} ms`}
                                color="text-orange-600"
                            />

                            <SummaryCard
                                icon={
                                    <Gauge size={30}/>
                                }
                                title="Average Throughput"
                                value={averageThroughput}
                                color="text-green-600"
                            />

                        </div>
                        <div className="
                        bg-white
                        shadow-lg
                        rounded-xl
                        overflow-hidden
                        ">

                            <div className="px-6 py-4 border-b">

                                <h2 className="text-xl font-semibold">

                                    Benchmark Results

                                </h2>

                                <p className="text-gray-500 text-sm mt-1">

                                    Performance metrics returned from the backend benchmark suite.

                                </p>

                            </div>

                            <div className="overflow-x-auto">

                                <table className="min-w-[900px] w-full">

                                    <thead className="bg-gray-100">

                                        <tr>

                                            <th className="p-3 text-left">
                                                Benchmark
                                            </th>

                                            <th className="p-3 text-center">
                                                Average
                                            </th>

                                            <th className="p-3 text-center">
                                                Minimum
                                            </th>

                                            <th className="p-3 text-center">
                                                Maximum
                                            </th>

                                            <th className="p-3 text-center">
                                                P95
                                            </th>

                                            <th className="p-3 text-center">
                                                Throughput
                                            </th>

                                        </tr>

                                    </thead>

                                    <tbody>

                                        {
                                            results.map((item,index)=>(

                                                <tr
                                                    key={index}
                                                    className="
                                                    border-t
                                                    hover:bg-gray-50
                                                    transition
                                                    "
                                                >

                                                    <td className="p-3 font-medium whitespace-nowrap">

                                                        {item.benchmarkName}

                                                    </td>

                                                    <td className="p-3 text-center whitespace-nowrap">

                                                        {item.averageLatency.toFixed(2)} ms

                                                    </td>

                                                    <td className="p-3 text-center whitespace-nowrap">

                                                        {item.minLatency.toFixed(2)} ms

                                                    </td>

                                                    <td className="p-3 text-center whitespace-nowrap">

                                                        {item.maxLatency.toFixed(2)} ms

                                                    </td>

                                                    <td className="p-3 text-center whitespace-nowrap">

                                                        {item.p95Latency.toFixed(2)} ms

                                                    </td>

                                                    <td className="p-3 text-center whitespace-nowrap">

                                                        {item.throughput.toFixed(3)}

                                                    </td>

                                                </tr>

                                            ))
                                        }

                                    </tbody>

                                </table>

                            </div>

                        </div>

                    </>

                }

            </div>

        </div>

    );

}

function SummaryCard({

    icon,
    title,
    value,
    color

}) {

    return (

        <div className="
        bg-white
        shadow-lg
        rounded-xl
        p-5
        flex
        items-center
        gap-4
        ">

            <div className={color}>

                {icon}

            </div>

            <div>

                <p className="text-gray-500 text-sm">

                    {title}

                </p>

                <h3 className="text-2xl font-bold mt-1">

                    {value}

                </h3>

            </div>

        </div>

    );

}

export default Benchmark;