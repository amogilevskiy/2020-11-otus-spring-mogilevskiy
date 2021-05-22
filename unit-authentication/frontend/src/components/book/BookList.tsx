import React from 'react';
import {Space, Table, Tag} from 'antd';
import Book from "../../domain/Book";
import Author from "../../domain/Author";
import {PaginatedList} from "../../services/PaginatedList";


interface BookListProps {
    books: PaginatedList<Book>;
    loading: boolean;
    onClick: (book: Book) => void;
    onEdit: (book: Book) => void;
    onDelete: (book: Book) => void;
    onPageChange: (page: number) => void;
}

const BookList: React.FC<BookListProps> = ({books, loading, onClick, onEdit, onDelete, onPageChange}) => {

    const columns = [
        {
            title: 'Id',
            dataIndex: 'id',
        },
        {
            title: 'Title',
            dataIndex: 'title',
        },
        {
            title: 'Genre',
            dataIndex: ['genre', 'title'],
        },
        {
            title: 'Authors',
            dataIndex: 'authors',
            render: (authors: Author[]) => (
                <span>
        {authors.map(author => {
            const color = 'geekblue';
            return (
                <Tag color={color} key={author.id}>
                    {author.firstName} {author.lastName}
                </Tag>
            );
        })}
      </span>
            ),
        },
        {
            title: '',
            key: 'options',
            render: (text: any, record: any) => (
                <Space size="middle">
                    <a onClick={(event) => {
                        event.stopPropagation();
                        onEdit(record);
                    }}>Edit</a>
                    <a onClick={(event) => {
                        event.stopPropagation();
                        onDelete(record);
                    }}>Delete</a>
                </Space>

            ),
        },
    ];

    return <Table dataSource={books.content}
                  pagination={{
                      total: books.totalElements,
                      pageSize: books.size,
                      current: books.number + 1,
                      onChange: onPageChange,
                  }}
                  loading={loading}
                  columns={columns}
                  rowKey="id"
                  onRow={(book: Book, _) => {
                      return {
                          onClick: _ => {
                              onClick(book);
                          },
                      };
                  }}
    />;
};

export default BookList;