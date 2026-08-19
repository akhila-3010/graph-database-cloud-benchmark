import { useState } from "react";
import {
    Upload,
    FileCheck,
    AlertCircle,
    LoaderCircle
} from "lucide-react";
import api from "../services/api";

function Dataset() {

    const [file, setFile] = useState(null);

    const [result, setResult] = useState(null);

    const [loading, setLoading] = useState(false);

    const [progress, setProgress] = useState(0);

    const loadDataset = async () => {

        if (!file) {

            setResult({
                success: false,
                message: "Please select a CSV file."
            });

            return;

        }

        setResult(null);

        setLoading(true);

        setProgress(0);

        const timer = setInterval(() => {

            setProgress((oldValue) => {

                if (oldValue >= 95) {

                    return 95;

                }

                return oldValue + 5;

            });

        }, 300);

        try {

            const formData = new FormData();

            formData.append(
                "file",
                file
            );

            const response = await api.post(
                "/datasets/import",
                formData,
                {
                    headers: {
                        "Content-Type":
                            "multipart/form-data"
                    }
                }
            );

            clearInterval(timer);

            setProgress(100);

            setResult({

                success: true,

                message: response.data

            });

        }

        catch (error) {

            clearInterval(timer);

            setProgress(0);

            setResult({

                success: false,

                message:
                    error.response?.data ||
                    "Unable to import dataset."

            });

        }

        finally {

            setTimeout(() => {

                setLoading(false);

            }, 600);

        }

    };

    return (

        <div className="min-h-screen bg-gray-50 p-6">

            <div className="max-w-4xl mx-auto">

                <h1 className="text-3xl font-bold mb-6">
                    Dataset Management
                </h1>

                <div className="bg-white shadow-lg rounded-xl p-6">

                    <div className="flex items-center gap-3 mb-6">

                        <Upload
                            className="text-green-600"
                            size={35}
                        />

                        <div>

                            <h2 className="text-xl font-semibold">
                                Import Graph Dataset
                            </h2>

                            <p className="text-gray-500 text-sm">
                                Upload a CSV graph dataset into the selected database.
                            </p>

                        </div>

                    </div>

                    <div className="bg-gray-100 rounded-lg p-5 mb-5">

                        <label className="font-semibold block mb-3">
                            Select CSV File
                        </label>

                        <input
                            type="file"
                            accept=".csv"
                            onChange={(e) => {

                                setFile(e.target.files[0]);

                                setResult(null);

                            }}
                            className="w-full border rounded-lg bg-white p-2"
                        />

                        {
                            file &&

                            <div className="mt-4 text-sm">

                                <p>

                                    <strong>Selected File :</strong>

                                    {" "}

                                    {file.name}

                                </p>

                                <p className="text-gray-500 mt-1">

                                    Size :

                                    {" "}

                                    {(file.size / 1024).toFixed(2)}

                                    {" "}KB

                                </p>

                            </div>

                        }

                    </div>

                    <button
                        onClick={loadDataset}
                        disabled={loading}
                        className="
                        bg-green-600
                        hover:bg-green-700
                        disabled:bg-gray-400
                        text-white
                        px-6
                        py-2
                        rounded-lg
                        transition
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
                                ? `Importing... ${progress}%`
                                : "Import Dataset"
                        }

                    </button>

                    {
                        loading &&

                        <div className="mt-6">

                            <div className="flex justify-between text-sm font-medium mb-2">

                                <span>
                                    Upload Progress
                                </span>

                                <span>

                                    {progress}%

                                </span>

                            </div>

                            <div className="w-full bg-gray-200 rounded-full h-4">

                                <div
                                    className="
                                    bg-green-600
                                    h-4
                                    rounded-full
                                    transition-all
                                    duration-300
                                    "
                                    style={{
                                        width: `${progress}%`
                                    }}
                                />

                            </div>

                            <p className="text-gray-500 text-sm mt-2">

                                Please wait while importing the dataset...

                            </p>

                        </div>

                    }
                    {
                        result &&

                        <div
                            className={`
                            mt-6
                            rounded-xl
                            border
                            p-5
                            ${
                                result.success
                                ? "border-green-300 bg-green-50"
                                : "border-red-300 bg-red-50"
                            }
                            `}
                        >

                            <div className="flex items-center gap-3 mb-3">

                                {
                                    result.success
                                    ?

                                    <FileCheck
                                        className="text-green-600"
                                        size={28}
                                    />

                                    :

                                    <AlertCircle
                                        className="text-red-600"
                                        size={28}
                                    />

                                }

                                <h3 className="text-lg font-semibold">

                                    {
                                        result.success
                                        ? "Dataset Imported Successfully"
                                        : "Dataset Import Failed"
                                    }

                                </h3>

                            </div>

                            <div
                                className="
                                bg-white
                                border
                                rounded-lg
                                p-4
                                text-sm
                                whitespace-pre-wrap
                                break-words
                                "
                            >

                                {result.message}

                            </div>

                        </div>

                    }

                </div>

            </div>

        </div>

    );

}

export default Dataset;