import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { FiPower, FiEdit, FiTrash2 } from 'react-icons/fi';

import api from '../../services/api';

import './styles.css';
import logo from '../../assets/logo.svg';

export default function Book() {
    const [books, setBooks] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);


    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');

    const navigate = useNavigate();

    useEffect(() => {
        if (!accessToken) {
            navigate('/');
            return;
        }

        handleGetBooks();
    }, [accessToken, navigate]);

    async function handleGetBooks() {
        try {
            const response = await api.get('/api/book/v1', {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                },
                params: {
                    page: page,
                    size: 10,
                    direction: 'asc',
                    sort: 'title'
                }
            });


            setBooks([...books, ...response.data._embedded.books]);
            setPage(response.data.page.number + 1);
            setTotalPages(response.data.page.totalPages);
        } catch (err) {
            alert('Error fetching books, please try again.');
        }
    }

    async function handleLogout() {
        localStorage.clear();
        navigate('/');
    }

    async function handleDeleteBook(id) {
        try {
            await api.delete(`/api/book/v1/${id}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            });

            setBooks(books.filter(book => book.id !== id));
        } catch (err) {
            alert('Error deleting book, please try again.');
        }
    }

    async function handleEditBook(id) {
            navigate(`/book/new/${id}`);
    }

    return (
        <div className="book-container">
            <header>
                <img src={logo} alt="Logo" />
                <span>Welcome, <strong>{username}</strong>!</span>
                <Link className="button" to="/book/new/0">Add Book</Link>
                <button type="button" onClick={handleLogout}>
                    <FiPower size={20} color="#251FC5" />
                </button>
            </header>

            <h1>Registered Book</h1>

            <ul>
                {books.map(book => (
                    <li key={book.id}>
                        <strong>Title:</strong>
                        <p>{book.title}</p>

                        <strong>Author:</strong>
                        <p>{book.author}</p>

                        <strong>Price:</strong>
                        <p>{new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(book.price)}</p>

                        <strong>Release Date:</strong>
                        <p>{new Intl.DateTimeFormat('pt-BR', { timeZone: 'UTC' }).format(new Date(book.launchDate))}</p>

                        <button type="button" onClick={() => handleEditBook(book.id)}>
                            <FiEdit size={20} color="#251FC5" />
                        </button>

                        <button type="button" onClick={() => handleDeleteBook(book.id)}>
                            <FiTrash2 size={20} color="#251FC5" />
                        </button>
                    </li>
                ))}
            </ul>

            {page < totalPages && (
                <button className="button" onClick={handleGetBooks}>Load more</button>
            )}
        </div>
    )
}