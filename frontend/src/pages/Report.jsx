import { useState } from "react";
import {
    FileText,
    Download,
    Database,
    LoaderCircle,
    CheckCircle2
} from "lucide-react";
import api from "../services/api";

function Report() {

    const [report, setReport] = useState(null);

    const [loading, setLoading] = useState(false);

    const [downloading, setDownloading] = useState(false);

    const [downloadProgress, setDownloadProgress] = useState(0);

    const [downloadMessage, setDownloadMessage] =
            useState("");

    const loadReport = async () => {

        try {

            setLoading(true);

            const response =
                    await api.get("/report");

            setReport(response.data);

        }

        catch {

            alert(
                "Unable to load report."
            );

        }

        finally {

            setLoading(false);

        }

    };

    const exportFile = async (type) => {

        try {

            setDownloading(true);

            setDownloadProgress(0);

            setDownloadMessage(
                    "Preparing download..."
            );

            const response =
                    await api.get(

                        `/report/download/${type}`,

                        {

                            responseType: "blob",

                            onDownloadProgress:
                            (progressEvent) => {

                                if (progressEvent.total) {

                                    const percent =
                                            Math.round(

                                        (
                                            progressEvent.loaded
                                            * 100
                                        )
                                        /
                                        progressEvent.total

                                    );

                                    setDownloadProgress(
                                            percent
                                    );

                                    setDownloadMessage(
                                            "Downloading..."
                                    );

                                }

                            }

                        }

                    );

            const url =
                    window.URL.createObjectURL(

                        new Blob(
                            [response.data]
                        )

                    );

            const link =
                    document.createElement("a");

            link.href = url;

            link.download =
                    type === "json"
                    ?
                    "benchmark-report.json"
                    :
                    "benchmark-report.csv";

            document.body.appendChild(link);

            link.click();

            link.remove();

            setDownloadProgress(100);

            setDownloadMessage(
                    "Download Started"
            );

            setTimeout(() => {

                setDownloading(false);

                setDownloadProgress(0);

                setDownloadMessage("");

            },1500);

        }

        catch {

            setDownloading(false);

            setDownloadProgress(0);

            setDownloadMessage("");

            alert(
                "Download failed."
            );

        }

    };

    return (

        <div className="min-h-screen bg-gray-50 p-6">

            <div className="max-w-7xl mx-auto">

                <h1 className="text-3xl font-bold mb-6">

                    Benchmark Report

                </h1>

                <div className="bg-white shadow-lg rounded-xl p-6 mb-6">

                    <div className="flex items-center gap-3 mb-6">

                        <FileText
                            className="text-blue-600"
                            size={36}
                        />

                        <div>

                            <h2 className="text-xl font-semibold">

                                Generate Benchmark Report

                            </h2>

                            <p className="text-gray-500 text-sm">

                                View benchmark summary and download reports.

                            </p>

                        </div>

                    </div>

                    <div className="flex flex-wrap gap-3">

                        <button
                            onClick={loadReport}
                            disabled={loading}
                            className="
                            bg-blue-600
                            hover:bg-blue-700
                            disabled:bg-gray-400
                            text-white
                            px-6
                            py-2
                            rounded-lg
                            flex
                            items-center
                            gap-2
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
                                "Loading..."
                                :
                                "View Report"
                            }

                        </button>

                        <button
                            onClick={() =>
                                exportFile("json")
                            }
                            disabled={downloading}
                            className="
                            bg-green-600
                            hover:bg-green-700
                            disabled:bg-gray-400
                            text-white
                            px-5
                            py-2
                            rounded-lg
                            flex
                            items-center
                            gap-2
                            "
                        >

                            <Download size={18}/>

                            JSON

                        </button>

                        <button
                            onClick={() =>
                                exportFile("csv")
                            }
                            disabled={downloading}
                            className="
                            bg-purple-600
                            hover:bg-purple-700
                            disabled:bg-gray-400
                            text-white
                            px-5
                            py-2
                            rounded-lg
                            flex
                            items-center
                            gap-2
                            "
                        >

                            <Download size={18}/>

                            CSV

                        </button>

                    </div>

                    {
                        downloading &&

                        <div className="mt-6">

                            <div className="flex justify-between mb-2">

                                <span className="font-medium flex items-center gap-2">

                                    {
                                        downloadProgress === 100

                                        ?

                                        <CheckCircle2
                                            className="text-green-600"
                                            size={18}
                                        />

                                        :

                                        <LoaderCircle
                                            className="animate-spin text-blue-600"
                                            size={18}
                                        />

                                    }

                                    {downloadMessage}

                                </span>

                                <span className="font-semibold">

                                    {downloadProgress}%

                                </span>

                            </div>

                            <div className="w-full bg-gray-200 rounded-full h-3">

                                <div
                                    className="
                                    h-3
                                    bg-green-600
                                    rounded-full
                                    transition-all
                                    duration-300
                                    "
                                    style={{
                                        width:
                                        `${downloadProgress}%`
                                    }}
                                />

                            </div>

                        </div>

                    }

                </div>

                {
                    report &&

                    <>
                        <div className="
                            bg-white
                            shadow-lg
                            rounded-xl
                            p-6
                            mb-6
                        ">

                            <div className="flex items-center gap-4">

                                <Database
                                    className="text-green-600"
                                    size={35}
                                />

                                <div>

                                    <p className="text-gray-500 text-sm">

                                        Connected Database

                                    </p>


                                    <h2 className="text-2xl font-bold">

                                        {report.databaseName}

                                    </h2>


                                </div>


                            </div>



                            <div className="mt-5 bg-gray-50 rounded-lg p-4">


                                <p className="text-gray-600">

                                    <strong>
                                        Generated Time:
                                    </strong>

                                    {" "}

                                    {report.generatedTime}

                                </p>


                                <p className="text-gray-600 mt-2">

                                    <strong>
                                        Total Benchmarks:
                                    </strong>

                                    {" "}

                                    {report.benchmarks.length}

                                </p>


                            </div>


                        </div>





                        <div className="
                            bg-white
                            shadow-lg
                            rounded-xl
                            overflow-hidden
                        ">


                            <div className="px-6 py-4 border-b">


                                <h2 className="text-xl font-semibold">

                                    Benchmark Details

                                </h2>


                                <p className="text-gray-500 text-sm">

                                    Complete performance metrics

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
                                            report.benchmarks.map(

                                                (item,index)=>(


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

                                                        {item.averageLatency.toFixed(2)}
                                                        {" ms"}

                                                    </td>


                                                    <td className="p-3 text-center whitespace-nowrap">

                                                        {item.minLatency.toFixed(2)}
                                                        {" ms"}

                                                    </td>


                                                    <td className="p-3 text-center whitespace-nowrap">

                                                        {item.maxLatency.toFixed(2)}
                                                        {" ms"}

                                                    </td>


                                                    <td className="p-3 text-center whitespace-nowrap">

                                                        {item.p95Latency.toFixed(2)}
                                                        {" ms"}

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


export default Report;