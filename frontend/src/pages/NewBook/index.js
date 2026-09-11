import React, { useState, useEffect } from 'react';
import { useNavigate, Link, useParams } from 'react-router-dom';

import api from '../../services/api';

import './styles.css';
import logo from '../../assets/logo.svg';

import { FiArrowLeft } from 'react-icons/fi';

export default function NewBook() {
    const [id, setId] = useState(null);
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [launchDate, setLaunchDate] = useState('');
    const [price, setPrice] = useState('');

    const accessToken = localStorage.getItem('accessToken');

    const navigate = useNavigate();

    const { bookId } = useParams();

    useEffect(() => {
        if (bookId === '0') {
            return; // If bookId is 0, we are creating a new book, so no need to fetch data
        }

        fetchBook();
    }, [bookId]);


    async function fetchBook() {
        try {
            const response = await api.get(`/api/book/v1/${bookId}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            });

            const book = response.data;
            setId(book.id);
            setTitle(book.title);
            setAuthor(book.author);
            setLaunchDate(book.launchDate);
            setPrice(book.price);
        } catch (err) {
            alert('Error fetching book details, please try again.');
            navigate('/books');
        }
    }

    async function handleNewBook(e) {
        e.preventDefault();

        const data = {
            title,
            author,
            launchDate,
            price
        };

        const headers = {
            Authorization: `Bearer ${accessToken}`
        };

        try {
            if (bookId === '0') {
                await api.post('/api/book/v1', data, { headers });
            } else {
                data.id = id;
                await api.put(`/api/book/v1`, data, { headers });
            }
            
            navigate('/books');
        } catch (err) {
            alert('Error registering book, please try again.');
        }
    }

    return (
        <div className="new-book-container">
            <div className="content">
                <section className="form">
                    <img src={logo} alt="Logo" />
                    <h1>{bookId === '0' ? 'New' : 'Update'} Book</h1>
                    <p>Fill in the details of your book and click the button below to save it.</p>

                    <Link className="back-link" to="/books">
                        <FiArrowLeft size={16} color="#251FC5" />
                        Home
                    </Link>
                </section>

                <form onSubmit={handleNewBook}>
                    <input
                        value={title}
                        placeholder="Title"
                        onChange={(e) => setTitle(e.target.value)}
                    />
                    <input
                        value={author}
                        placeholder="Author"
                        onChange={(e) => setAuthor(e.target.value)}
                    />
                    <input
                        value={launchDate}
                        placeholder="Release Date"
                        type="date"
                        onChange={(e) => setLaunchDate(e.target.value)}
                    />
                    <input
                        value={price}
                        placeholder="Price"
                        onChange={(e) => setPrice(e.target.value)}
                    />

                    <button className="button" type="submit">{bookId === '0' ? 'Add' : 'Update'}</button>
                </form>
            </div>
        </div>
    )
}